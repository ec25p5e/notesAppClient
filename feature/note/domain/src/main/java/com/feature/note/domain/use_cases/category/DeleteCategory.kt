package com.feature.note.domain.use_cases.category

import android.util.Log
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

class DeleteCategory(
    private val categoryRepository: CategoryRepository,
    private val noteRepository: NoteRepository,
) {

    suspend operator fun invoke(category: Category): Boolean {
        val categoryId = category.id!!
        val noteByCategoryFlow = noteRepository.getNotesByCategory(categoryId)
        val listOfNote = noteByCategoryFlow.flattenToList()

        Log.d("DeleteCategory", listOfNote.toString())

        categoryRepository.deleteCategory(category)
        return true
    }

    private suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()
}