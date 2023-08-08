package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository

class GetCategoryById(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(categoryId: Int): Category {
        return categoryRepository.getCategoryById(categoryId)
    }
}