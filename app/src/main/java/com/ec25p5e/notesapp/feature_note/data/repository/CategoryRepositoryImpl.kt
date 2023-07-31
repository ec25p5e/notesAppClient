package com.ec25p5e.notesapp.feature_note.data.repository

import com.ec25p5e.notesapp.feature_note.data.data_source.CategoryDao
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val dao: CategoryDao
): CategoryRepository {

    override fun getCategories(): Flow<List<Category>> {
        return dao.getCategories()
    }

    override fun insertCategory(category: Category) {
        return dao.insertCategory(category)
    }
}