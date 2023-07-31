package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val repository: CategoryRepository
) {

    operator fun invoke(): Flow<List<Category>> {
        return repository.getCategories()
    }
}