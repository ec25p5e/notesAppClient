package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.core.data.local.encryption.CryptoManager
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class GetNotes(
    private val repository: NoteRepository,
) {
    suspend operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
        fetchFromRemote: Boolean = false,
    ): Flow<List<Note>> {
        return repository.getAllNotes(fetchFromRemote).data!!.map { notes ->
            notes.forEach { note ->
                note.title = AESEncryptor.decrypt(note.title).toString()
                note.content = AESEncryptor.decrypt(note.content).toString()
            }

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