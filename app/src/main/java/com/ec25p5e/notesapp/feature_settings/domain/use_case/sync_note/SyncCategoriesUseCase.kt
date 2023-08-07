package com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_settings.domain.models.SyncOption

class SyncCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
    dataStore: DataStorePreferenceImpl,
) {

    suspend operator fun invoke(options: SyncOption) {

    }
}