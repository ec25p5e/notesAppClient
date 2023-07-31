package com.ec25p5e.notesapp.feature_note.presentation.archive

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _state = mutableStateOf(ArchiveState())
    val state: State<ArchiveState> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotesForArchive(ArchiveOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: ArchiveEvent){
        when(event) {
            is ArchiveEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }

                getNotesForArchive(event.noteOrder)
            }
            is ArchiveEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is ArchiveEvent.DeArchiveNote -> {

            }
            is ArchiveEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotesForArchive(noteOrder: ArchiveOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesForArchive(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}