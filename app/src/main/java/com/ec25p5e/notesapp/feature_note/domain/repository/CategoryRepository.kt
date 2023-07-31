package com.ec25p5e.notesapp.feature_note.domain.repository

import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>

    fun insertCategory(category: Category)
}