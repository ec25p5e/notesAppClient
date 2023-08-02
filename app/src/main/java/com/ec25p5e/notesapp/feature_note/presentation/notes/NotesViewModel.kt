package com.ec25p5e.notesapp.feature_note.presentation.notes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var _filterCategory: Int = -1
    private var _noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)

    private val _isLoading = mutableStateOf(false)
    var isLoading: State<Boolean> = _isLoading

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {  _eventFlow.emit(UiEventNote.IsLoadingPage) }
        getNotes(_noteOrder)
        viewModelScope.launch {  _eventFlow.emit(UiEventNote.IsLoadingPage) }
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                _noteOrder = event.noteOrder

                if(state.value.noteOrder::class == event.noteOrder::class &&
                        state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }

                if(_filterCategory == -1) {
                    getNotes(_noteOrder)
                } else {
                    viewModelScope.launch {
                        getNotesByCategory(_filterCategory, _noteOrder)
                    }
                }
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    val deleteResponse = noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note

                    if(deleteResponse.uiText != null) {
                        _eventFlow.emit(UiEventNote.ShowSnackbar(deleteResponse.uiText))
                    } else {
                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(R.string.deleted_note)))
                    }
                }
            }
            is NotesEvent.FetchNote -> {

            }
            is NotesEvent.ArchiveNote -> {
                viewModelScope.launch {
                    event.note.id?.let { noteUseCases.archiveNote(it) }
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.FilterNotesByCategory -> {
                _filterCategory = event.categoryId

                viewModelScope.launch {
                    if(event.categoryId == 1) {
                        getNotes(_noteOrder)
                    } else {
                        getNotesByCategory(_filterCategory, _noteOrder)
                    }
                }
            }
            is NotesEvent.IsLoadingPage -> {
                _isLoading.value = !_isLoading.value
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        viewModelScope.launch {
            getNotesJob?.cancel()
            getNotesJob = noteUseCases.getNotes(noteOrder, true)
                .onEach { notes ->
                    _state.value = state.value.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getNotesByCategory(categoryId: Int, noteOrder: NoteOrder) {
        Log.i("NotesViewModel", categoryId.toString())

        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesByCategory(categoryId, noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = NoteOrder.Date(OrderType.Ascending)
                )
            }
            .launchIn(viewModelScope)
    }
}