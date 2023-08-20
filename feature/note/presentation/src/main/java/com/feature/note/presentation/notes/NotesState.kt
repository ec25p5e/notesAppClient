package com.feature.note.presentation.notes

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val noteToDelete: Note? = null,
    val isNoteToDelete: Boolean = false,
)