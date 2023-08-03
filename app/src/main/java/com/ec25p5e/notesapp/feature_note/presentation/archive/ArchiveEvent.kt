package com.ec25p5e.notesapp.feature_note.presentation.archive

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveOrder

sealed class ArchiveEvent {
    data class Order(val noteOrder: ArchiveOrder): ArchiveEvent()
    data class DeArchiveNote(val noteId: Int): ArchiveEvent()

    object ToggleOrderSection: ArchiveEvent()
}