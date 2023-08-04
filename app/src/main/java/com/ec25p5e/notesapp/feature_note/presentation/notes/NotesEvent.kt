package com.ec25p5e.notesapp.feature_note.presentation.notes

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class ArchiveNote(val noteId: Int): NotesEvent()
    data class FilterNotesByCategory(val categoryId: Int): NotesEvent()
    data class SetNoteToDelete(val note: Note): NotesEvent()
    data class ExportCsv(val noteId: Int): NotesEvent()
    data class ConvertInAudio(val noteId: Int): NotesEvent()
    data class ShareNote(val noteId: Int): NotesEvent()
    data class CopyNote(val noteId: Int): NotesEvent()

    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
    object IsLoadingPage: NotesEvent()
    object FetchNote: NotesEvent()
    object IsDeletingNote: NotesEvent()
    object DeleteNote: NotesEvent()
}