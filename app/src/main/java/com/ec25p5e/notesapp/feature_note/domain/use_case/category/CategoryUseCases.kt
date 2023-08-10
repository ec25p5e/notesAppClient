package com.ec25p5e.notesapp.feature_note.domain.use_case.category


data class CategoryUseCases(
    val getCategories: GetCategories,
    val getCategoryById: GetCategoryById,
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory,
    val pushCategory: PushCategory,
)