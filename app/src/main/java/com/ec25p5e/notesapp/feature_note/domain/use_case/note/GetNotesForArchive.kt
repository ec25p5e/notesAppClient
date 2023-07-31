package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesForArchive(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: ArchiveOrder = ArchiveOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotesForArchive().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is ArchiveOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is ArchiveOrder.Date -> notes.sortedBy { it.timestamp }
                        is ArchiveOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is ArchiveOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is ArchiveOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is ArchiveOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}