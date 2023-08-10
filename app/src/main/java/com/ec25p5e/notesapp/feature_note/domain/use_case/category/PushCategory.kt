package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository

class PushCategory(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke() {
        repository.pushCategories()
    }
}