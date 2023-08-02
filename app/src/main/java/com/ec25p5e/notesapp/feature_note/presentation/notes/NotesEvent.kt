package com.ec25p5e.notesapp.feature_note.presentation.notes

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    data class ArchiveNote(val note: Note): NotesEvent()
    data class FilterNotesByCategory(val categoryId: Int): NotesEvent()

    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
    object IsLoadingPage: NotesEvent()
    object FetchNote: NotesEvent()
}