package com.feature.note.domain.repository

import com.feature.note.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAllCategories(fetchFromRemote: Boolean): Flow<List<Category>>

    suspend fun pushCategories(): Resource<List<CategoryResponse>>

    fun getCategoryById(categoryId: Int): Category

    fun insertCategory(category: Category)

    fun deleteCategory(category: Category)
}