package com.ec25p5e.notesapp.feature_note.domain.use_case.category

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.presentation.util.CategoryResult
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AddCategory(
    private val repository: CategoryRepository,
    private val preferenceImpl: DataStorePreferenceImpl
) {

    operator fun invoke(category: Category): CategoryResult {
        val titleError = if(category.name.isBlank()) AuthError.FieldEmpty else null

        if(titleError != null)
            return CategoryResult(titleError)

        /* val cryptedCategory = category.copy(
            name = AESEncryptor.encrypt(category.name)!!,
            userId = runBlocking(Dispatchers.IO) {
                preferenceImpl.getPreference(DataStorePreferenceConstants.USER_ID, "").first()
            }
        ) */

        repository.insertCategory(category)
        return CategoryResult(
            result = true
        )
    }
}