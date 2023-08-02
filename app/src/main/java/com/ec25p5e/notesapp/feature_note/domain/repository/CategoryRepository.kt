package com.ec25p5e.notesapp.feature_note.domain.repository

import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAllCategories(fetchFromRemote: Boolean): Flow<List<Category>>

    suspend fun insertCategory(category: Category): SimpleResource
}