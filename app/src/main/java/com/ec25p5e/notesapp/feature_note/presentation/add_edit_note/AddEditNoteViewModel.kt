package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState: State<StandardTextFieldState> = _titleState

    private val _contentState = mutableStateOf(StandardTextFieldState())
    val contentState: State<StandardTextFieldState> = _contentState

    private val _isArchivedState = mutableStateOf(false)
    val isArchivedState: State<Boolean> = _isArchivedState

    private val _colorState = mutableStateOf(Note.noteColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _noteCategory = mutableStateOf(1)
    val noteCategory: State<Int> = _noteCategory

    private val _noteBackground = mutableStateOf(R.drawable.ic_no_image_note)
    val noteBackground: State<Int> = _noteBackground

    private val _state = mutableStateOf(AddEditNoteState(
        isAutoSaveEnabled = true,
        isSharing = true
    ))
    val state: State<AddEditNoteState> = _state

    private val _chosenImageUri = mutableStateOf<ArrayList<Uri?>>(ArrayList())
    val chosenImageUri: State<ArrayList<Uri?>> = _chosenImageUri

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                        _noteCategory.value = note.categoryId
                        _chosenImageUri.value = uriArray
                        _noteBackground.value = note.background
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
            is AddEditNoteEvent.IsSaveNote -> {
                _state.value = _state.value.copy(
                    isSaving = _state.value.isSaving.not()
                )
            }
            is AddEditNoteEvent.ToggleArchived -> {
                _isArchivedState.value = _isArchivedState.value.not()
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
                }
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    val imageArray: ArrayList<String> = ArrayList()
                    _chosenImageUri.value.forEach { uri -> imageArray.add(uri.toString()) }

                    val noteInsert = Note(
                        title = titleState.value.text,
                        content = contentState.value.text,
                        timestamp = System.currentTimeMillis(),
                        color = colorState.value,
                        id = currentNoteId,
                        categoryId = noteCategory.value,
                        image = imageArray,
                        background = _noteBackground.value
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
                        _eventFlow.emit(UiEventNote.ShowLoader)
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