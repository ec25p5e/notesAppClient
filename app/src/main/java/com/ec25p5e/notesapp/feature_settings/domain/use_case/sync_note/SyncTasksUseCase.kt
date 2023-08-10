package com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_settings.domain.models.SyncOption
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository

class SyncTasksUseCase(
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(option: SyncOption) {

    }
}