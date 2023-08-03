package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.exceptions.InvalidNoteException
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

    private val _colorState = mutableStateOf(Note.noteColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _noteCategory = mutableStateOf(1)
    val noteCategory: State<Int> = _noteCategory

    private val _isSaving = mutableStateOf(false)
    val isSaving: State<Boolean> = _isSaving

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri: State<Uri?> = _chosenImageUri

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _titleState.value = titleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _contentState.value = _contentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _colorState.value = note.color
                        _noteCategory.value = note.categoryId
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
                _isSaving.value = !_isSaving.value
            }
            is AddEditNoteEvent.ChangeColor -> {
                _colorState.value = event.color
            }
            is AddEditNoteEvent.ChangeCategoryColor -> {
                _noteCategory.value = event.categoryId
            }
            is AddEditNoteEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    val noteInsert = Note(
                        title = titleState.value.text,
                        content = contentState.value.text,
                        timestamp = System.currentTimeMillis(),
                        color = colorState.value,
                        id = currentNoteId,
                        categoryId = noteCategory.value,
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