package com.feature.note.domain.use_cases.note

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