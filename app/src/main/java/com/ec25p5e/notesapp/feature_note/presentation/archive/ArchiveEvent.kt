package com.ec25p5e.notesapp.feature_note.presentation.archive

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveOrder

sealed class ArchiveEvent {
    data class Order(val noteOrder: ArchiveOrder): ArchiveEvent()
    data class DeleteNote(val note: Note): ArchiveEvent()
    data class DeArchiveNote(val note: Note): ArchiveEvent()

    object ToggleOrderSection: ArchiveEvent()
}