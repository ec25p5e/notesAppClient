package com.feature.note.domain.use_cases.category


data class CategoryUseCases(
    val getCategories: GetCategories,
    val getCategoryById: GetCategoryById,
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory,
    val pushCategory: PushCategory,
)