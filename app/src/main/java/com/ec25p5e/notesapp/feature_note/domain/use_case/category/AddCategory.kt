package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import android.content.res.Resources
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError
import com.ec25p5e.notesapp.feature_note.domain.exceptions.InvalidCategoryException
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.CategoryResult
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository

class AddCategory(
    private val repository: CategoryRepository
) {

    operator fun invoke(category: Category): CategoryResult {
        val titleError = if(category.name.isBlank()) AuthError.FieldEmpty else null

        if(titleError != null)
            return CategoryResult(titleError)

        repository.insertCategory(category)
        return CategoryResult(
            result = true
        )
    }
}