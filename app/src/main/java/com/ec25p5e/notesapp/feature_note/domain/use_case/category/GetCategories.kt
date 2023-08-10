package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.util.CategoryOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(
        categoryOrder: CategoryOrder = CategoryOrder.Title(OrderType.Ascending),
        fetchFromRemote: Boolean
    ): Flow<List<Category>> {
        return repository.getAllCategories(fetchFromRemote).map { categories ->
            /* categories.forEach { category ->
                category.name = AESEncryptor.decrypt(category.name)!!
            } */

            when(categoryOrder.orderType) {
                is OrderType.Ascending -> {
                    when(categoryOrder) {
                        is CategoryOrder.Title -> categories.sortedBy { it.name.lowercase() }
                        is CategoryOrder.Date -> categories.sortedBy { it.timestamp }
                    }
                }
                is OrderType.Descending -> {
                    when(categoryOrder) {
                        is CategoryOrder.Title -> categories.sortedByDescending { it.name.lowercase() }
                        is CategoryOrder.Date -> categories.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }
}