package com.ec25p5e.notesapp.feature_cam.domain.use_case

import com.ec25p5e.notesapp.feature_cam.data.mapper.toCategory
import com.ec25p5e.notesapp.feature_cam.domain.model.Category
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository

class GetCategories(
    private val repository: CamRepository
) {

    suspend operator fun invoke(): List<Category> {
        val categories = repository.getCategories()
        return categories.map { it.toCategory() }
    }
}