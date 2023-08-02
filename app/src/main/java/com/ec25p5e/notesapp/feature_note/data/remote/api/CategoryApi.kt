package com.ec25p5e.notesapp.feature_note.data.remote.api

import com.ec25p5e.notesapp.core.data.dto.response.BasicApiResponse
import com.ec25p5e.notesapp.feature_note.data.remote.request.CreateCategoryRequest
import com.ec25p5e.notesapp.feature_note.data.remote.request.GetCategoriesRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoryApi {

    @POST("/api/category/categories")
    suspend fun getCategories(
        @Body request: GetCategoriesRequest
    ): BasicApiResponse<List<CategoryResponse>>

    @POST("/api/category/create")
    suspend fun createCategory(
        @Body request: CreateCategoryRequest
    ): BasicApiResponse<Unit>

    companion object {
        const val BASE_URL = "http://85.0.253.197:8080/"
    }
}