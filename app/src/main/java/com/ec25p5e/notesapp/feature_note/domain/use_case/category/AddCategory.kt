package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import android.content.res.Resources
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.feature_note.domain.exceptions.InvalidCategoryException
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository

class AddCategory(
    private val repository: CategoryRepository
) {

    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(note: Category) {
        if(note.name.isBlank())
            throw InvalidCategoryException(Resources.getSystem().getString(R.string.error_category_name_empty))

        repository.insertCategory(note)
    }
}