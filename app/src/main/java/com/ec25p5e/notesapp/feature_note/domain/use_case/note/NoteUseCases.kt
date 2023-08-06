package com.ec25p5e.notesapp.feature_note.domain.use_case.note

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote,
    val archiveNote: ArchiveNote,
    val getNotesForArchive: GetNotesForArchive,
    val getNotesByCategory: GetNoteByCategory,
    val dearchiveNote: DearchiveNote,
    val copyNote: CopyNote,
    val lockNote: LockNote,
    val unLockNote: UnLockNote
)