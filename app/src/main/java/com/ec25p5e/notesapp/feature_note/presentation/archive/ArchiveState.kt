package com.ec25p5e.notesapp.feature_note.presentation.archive

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveNoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType

data class ArchiveState(
    val notes: List<Note> = emptyList(),
    val noteOrder: ArchiveNoteOrder = ArchiveNoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isLoading: Boolean = false,
)
