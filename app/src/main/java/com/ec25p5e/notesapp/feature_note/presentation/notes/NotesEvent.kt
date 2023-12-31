package com.ec25p5e.notesapp.feature_note.presentation.notes

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class ArchiveNote(val noteId: Int): NotesEvent()
    data class LockNote(val noteId: Int): NotesEvent()

    data class UnLockNote(val noteId: Int): NotesEvent()
    data class FilterNotesByCategory(val categoryId: Int): NotesEvent()
    data class SetNoteToDelete(val note: Note): NotesEvent()
    data class CopyNote(val noteId: Int): NotesEvent()

    data object RestoreNote: NotesEvent()
    data object ToggleOrderSection: NotesEvent()
    data object IsLoadingPage: NotesEvent()
    data object IsDeletingNote: NotesEvent()
    data object DeleteNote: NotesEvent()
}