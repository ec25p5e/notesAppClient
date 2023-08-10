package com.ec25p5e.notesapp.feature_note.domain.repository

import com.ec25p5e.notesapp.core.data.dto.response.BasicApiResponse
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.data.remote.response.CategoryResponse
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAllCategories(fetchFromRemote: Boolean): Flow<List<Category>>

    suspend fun pushCategories(): Resource<List<CategoryResponse>>

    fun getCategoryById(categoryId: Int): Category

    fun insertCategory(category: Category)

    fun deleteCategory(category: Category)
}