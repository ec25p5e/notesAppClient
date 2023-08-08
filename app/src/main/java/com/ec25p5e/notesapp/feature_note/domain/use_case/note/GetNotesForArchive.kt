package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveNoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesForArchive(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: ArchiveNoteOrder = ArchiveNoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotesForArchive().map { notes ->
            notes.forEach { note ->
                note.title = AESEncryptor.decrypt(note.title)!!
                note.content = AESEncryptor.decrypt(note.content)!!
            }

            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is ArchiveNoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is ArchiveNoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is ArchiveNoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is ArchiveNoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is ArchiveNoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is ArchiveNoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}