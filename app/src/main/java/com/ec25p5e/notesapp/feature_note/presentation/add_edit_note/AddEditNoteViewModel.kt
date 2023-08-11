package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.converters.ListPairConverter
import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.models.PathProperties
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private var dataStore: DataStore<AppSettings>,
    private var aesEncryptor: AESEncryptor,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState: State<StandardTextFieldState> = _titleState

    private val _contentState = mutableStateOf(StandardTextFieldState())
    val contentState: State<StandardTextFieldState> = _contentState

    private val _pinState = mutableStateOf(StandardTextFieldState())
    val pinState: State<StandardTextFieldState> = _pinState

    private val _isArchivedState = mutableStateOf(false)
    val isArchivedState: State<Boolean> = _isArchivedState

    private val _colorState = mutableStateOf(Note.noteColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _noteCategory = mutableStateOf(1)
    val noteCategory: State<Int> = _noteCategory

    private val _noteBackground = mutableStateOf(R.drawable.ic_no_image_note)
    val noteBackground: State<Int> = _noteBackground

    private val _isLockedNote = mutableStateOf(false)
    val isLockedNote: State<Boolean> = _isLockedNote

    private var _paths = mutableStateListOf<Pair<Path, PathProperties>>()
    val paths: SnapshotStateList<Pair<Path, PathProperties>> = _paths

    private val _pathsUndone = mutableStateListOf<Pair<Path, PathProperties>>()
    val pathsUndone: SnapshotStateList<Pair<Path, PathProperties>> = _pathsUndone

    private val _state = mutableStateOf(AddEditNoteState(
        isAutoSaveEnabled = runBlocking { dataStore.data.first().isAutoSaveEnabled },
        isSharing = runBlocking { dataStore.data.first().isSharingEnabled },
        lockedMode = runBlocking { dataStore.data.first().unlock },
    ))
    val state: State<AddEditNoteState> = _state

    private val _unlockPinState = mutableStateOf(AddEditNotePinState())
    val unlockPinState: State<AddEditNotePinState> = _unlockPinState

    @SuppressLint("MutableCollectionMutableState")
    private val _chosenImageUri = mutableStateOf<ArrayList<Uri?>>(ArrayList())
    val chosenImageUri: State<ArrayList<Uri?>> = _chosenImageUri

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var textToSpeech:TextToSpeech? = null

    var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    val uriArray: ArrayList<Uri?> = ArrayList()

                    noteUseCases.getNote(noteId)?.also { note ->
                        note.image.forEach { image -> uriArray.add(Uri.parse(image))}

                        currentNoteId = note.id
                        _titleState.value = titleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _contentState.value = _contentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _isArchivedState.value = note.isArchived
                        _colorState.value = note.color
                        _noteCategory.value = 1 // note.categoryId
                        _chosenImageUri.value = uriArray
                        _noteBackground.value = note.background
                        _isLockedNote.value = note.isLocked
                        // _paths = mutableStateListOf(note.imagePath!![0])
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _titleState.value = titleState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            titleState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _contentState.value = _contentState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _contentState.value = _contentState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _contentState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredPin -> {
                _pinState.value = pinState.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangePinFocus -> {
                _pinState.value = _pinState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        _pinState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ConvertInAudio -> {

            }
            is AddEditNoteEvent.DrawingMode -> {
                _state.value = _state.value.copy(
                    isDrawing = !_state.value.isDrawing
                )
            }
            is AddEditNoteEvent.UnlockNote -> {
                viewModelScope.launch {
                    val targetValue = _state.value.lockedMode.valueToUnlock
                    val enteredValue = _pinState.value.text

                    if(targetValue == enteredValue) {
                        _unlockPinState.value = _unlockPinState.value.copy(
                            isPinError = false,
                            isNoteUnlocked = !unlockPinState.value.isNoteUnlocked
                        )
                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.note_unlocked_successfully)))
                    } else {
                        _unlockPinState.value = unlockPinState.value.copy(
                            isPinError = !unlockPinState.value.isPinError,
                            isNoteUnlocked = false
                        )
                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.wrong_ping_entered)))
                    }
                }
            }
            is AddEditNoteEvent.TogglePinError -> {
                _unlockPinState.value = unlockPinState.value.copy(
                    isPinError = !unlockPinState.value.isPinError
                )
                _pinState.value = _pinState.value.copy(
                    text = "",
                    error = null
                )
            }
            is AddEditNoteEvent.ReadNote -> {
                textToSpeech = TextToSpeech(
                    event.context
                ) {
                    if(it == TextToSpeech.SUCCESS) {
                        textToSpeech?.let { txtToSpeech ->
                            txtToSpeech.language = Locale.ITALIAN
                            txtToSpeech.setSpeechRate(1.0f)
                            txtToSpeech.speak(
                                _titleState.value.text,
                                TextToSpeech.QUEUE_ADD,
                                null,
                                null
                            )
                        }

                        Executors.newSingleThreadScheduledExecutor().schedule({
                            textToSpeech?.let { txtToSpeech ->
                                txtToSpeech.language = Locale.ITALIAN
                                txtToSpeech.setSpeechRate(1.0f)
                                txtToSpeech.speak(
                                    _contentState.value.text,
                                    TextToSpeech.QUEUE_ADD,
                                    null,
                                    null
                                )
                            }
                        }, 3, TimeUnit.SECONDS)
                    }
                }
            }
            is AddEditNoteEvent.IsSaveNote -> {
                _state.value = _state.value.copy(
                    isSaving = _state.value.isSaving.not()
                )
            }
            is AddEditNoteEvent.ToggleArchived -> {
                _isArchivedState.value = _isArchivedState.value.not()
            }
            is AddEditNoteEvent.ToggleLockMode -> {
                _isLockedNote.value = _isLockedNote.value.not()
            }
            is AddEditNoteEvent.ToggleCategoryModal -> {
                _state.value = _state.value.copy(
                    isCategoryModalOpen = !_state.value.isCategoryModalOpen
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _colorState.value = event.color
            }
            is AddEditNoteEvent.ChangeCategoryColor -> {
                _noteCategory.value = event.categoryId
            }
            is AddEditNoteEvent.PickImage -> {
                _chosenImageUri.value = event.uri.toMutableList() as ArrayList<Uri?>
            }
            is AddEditNoteEvent.ChangeBgImage -> {
                _noteBackground.value = event.bgImage
            }
            is AddEditNoteEvent.DeleteImage -> {
                viewModelScope.launch {
                    _chosenImageUri.value.remove(event.uri)
                    _chosenImageUri.value = _chosenImageUri.value
                    _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.image_removed)))
                }
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEventNote.ShowLoader)

                    val imageArray: ArrayList<String> = ArrayList()
                    _chosenImageUri.value.forEach { uri -> imageArray.add(uri.toString()) }

                    val noteInsert = Note(
                        title = _titleState.value.text,
                        content = contentState.value.text,
                        timestamp = System.currentTimeMillis(),
                        color = colorState.value,
                        id = currentNoteId,
                        categoryId = _noteCategory.value,
                        image = imageArray,
                        background = _noteBackground.value,
                        isLocked = _isLockedNote.value,
                        imagePath = _paths.toList(),
                    )

                    val addingResult = noteUseCases.addNote(noteInsert)

                    if(addingResult.titleError != null) {
                        _titleState.value = titleState.value.copy(
                            error = addingResult.titleError
                        )
                    }

                    if(addingResult.contentError != null) {
                        _contentState.value = contentState.value.copy(
                            error = addingResult.contentError
                        )
                    }

                    if(addingResult.isCorrect()) {
                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id =
                            if(currentNoteId == null) {
                                R.string.note_created_text
                            } else {
                                R.string.note_updated_text
                            }
                        )))

                        _eventFlow.emit(UiEventNote.SaveNote)
                    }
                }
            }
        }
    }
}