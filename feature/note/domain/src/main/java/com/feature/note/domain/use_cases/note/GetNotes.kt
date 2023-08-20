package com.feature.note.domain.use_cases.note

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.feature.note.presentation.util.NoteOrder
import com.feature.note.presentation.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository,
) {
    suspend operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
        fetchFromRemote: Boolean = false,
    ): Flow<List<Note>> {
        return repository.getAllNotes(fetchFromRemote).data!!.map { notes ->
            /* notes.forEach { note ->
                note.title = AESEncryptor.decrypt(note.title).toString()
                note.content = AESEncryptor.decrypt(note.content).toString()
            } */

            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}