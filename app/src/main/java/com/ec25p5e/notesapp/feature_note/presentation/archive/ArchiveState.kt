package com.ec25p5e.notesapp.feature_note.presentation.archive

import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType

data class ArchiveState(
    val notes: List<Note> = emptyList(),
    val noteOrder: ArchiveOrder = ArchiveOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
