package com.feature.note.presentation.notes

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.scheduler.AndroidAlarmScheduler
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.feature.note.presentation.notes.NotesEvent
import com.feature.note.presentation.notes.NotesState
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
    private val noteUseCases: NoteUseCases,
    private val alarmScheduler: AndroidAlarmScheduler,
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var _filterCategory: Int = -1
    private var _noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null
    private var textToSpeech: TextToSpeech? = null

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
                if(state.value.noteToDelete != null) {
                    viewModelScope.launch {
                        noteUseCases.deleteNote(state.value.noteToDelete!!)
                        recentlyDeletedNote = state.value.noteToDelete!!

                        _state.value = _state.value.copy(
                            isNoteToDelete = true,
                            isDeleting = false,
                            noteToDelete = null
                        )

                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(R.string.deleted_note)))
                    }
                }
            }
            is NotesEvent.ArchiveNote -> {
                viewModelScope.launch {
                    noteUseCases.archiveNote(event.noteId)
                    _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.note_archived)))
                }
            }
            is NotesEvent.LockNote -> {
                viewModelScope.launch {
                    noteUseCases.lockNote(event.noteId)
                    _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.note_locked)))
                }
            }
            is NotesEvent.UnLockNote -> {
                viewModelScope.launch {
                    noteUseCases.unLockNote(event.noteId)
                    _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.note_unlocked)))
                }
            }
            is NotesEvent.CopyNote -> {
                viewModelScope.launch {
                    noteUseCases.copyNote(event.noteId)
                    _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.note_copied)))
                }
            }
            is NotesEvent.RestoreNote -> {

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
                _state.value = _state.value.copy(
                    isLoading = !_state.value.isLoading
                )
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotesEvent.IsDeletingNote -> {
                _state.value = _state.value.copy(
                    isDeleting = !_state.value.isDeleting
                )
            }
            is NotesEvent.SetNoteToDelete -> {
                _state.value = _state.value.copy(
                    noteToDelete = event.note,
                    isDeleting = true
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        viewModelScope.launch {
            getNotesJob?.cancel()
            getNotesJob = noteUseCases.getNotes(noteOrder, false)
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