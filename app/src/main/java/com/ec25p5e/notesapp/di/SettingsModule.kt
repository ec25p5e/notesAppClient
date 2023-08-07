package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note.SyncCategoriesUseCase
import com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note.SyncNoteUseCases
import com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note.SyncNotesUseCase
import com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note.SyncTasksUseCase
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsSyncUseCases(
        noteRepository: NoteRepository,
        categoryRepository: CategoryRepository,
        taskRepository: TaskRepository,
        dataStore: DataStorePreferenceImpl
    ): SyncNoteUseCases {
        return SyncNoteUseCases(
            syncNote = SyncNotesUseCase(noteRepository),
            syncCategories = SyncCategoriesUseCase(categoryRepository, dataStore),
            syncTasks = SyncTasksUseCase(taskRepository, dataStore)
        )
    }
}