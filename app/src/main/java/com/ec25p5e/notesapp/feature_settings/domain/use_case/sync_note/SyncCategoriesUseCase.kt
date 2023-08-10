package com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note

import android.util.Log
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.data.mapper.toCategory
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_settings.domain.models.SyncOption

class SyncCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {

    suspend operator fun invoke(options: SyncOption): SimpleResource? {
        if(options.isSyncCategories) {
            val response = categoryRepository.pushCategories()

            if(response.data != null) {
                response.data.forEach { categoryResponse ->
                    categoryRepository.insertCategory(categoryResponse.toCategory())
                }
            }
        }

        return null
    }
}