package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategories(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(fetchFromRemote: Boolean): Flow<List<Category>> {
        return repository.getAllCategories(fetchFromRemote)
    }
}