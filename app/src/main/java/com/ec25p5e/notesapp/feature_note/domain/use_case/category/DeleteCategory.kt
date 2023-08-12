package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import androidx.lifecycle.asLiveData
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class DeleteCategory(
    private val categoryRepository: CategoryRepository,
    private val noteRepository: NoteRepository,
) {

    operator fun invoke(category: Category): Boolean {
        val categoryId = category.id!!
        val noteByCategoryFlow = noteRepository.getNotesByCategory(categoryId)
        val noteByCategory: List<Note> = emptyList()

        noteByCategory.apply {
            noteByCategoryFlow.map {
                it.toList()
            }
        }

        noteByCategory.forEach { note -> noteRepository.deleteNote(note) }
        categoryRepository.deleteCategory(category)
        return true
    }
}