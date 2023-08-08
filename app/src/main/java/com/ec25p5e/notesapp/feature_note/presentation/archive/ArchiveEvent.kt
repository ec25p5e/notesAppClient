package com.ec25p5e.notesapp.feature_note.presentation.archive

import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveNoteOrder

sealed class ArchiveEvent {
    data class Order(val noteOrder: ArchiveNoteOrder): ArchiveEvent()
    data class DeArchiveNote(val noteId: Int): ArchiveEvent()

    object ToggleOrderSection: ArchiveEvent()
}