package com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note

data class SyncNoteUseCases(
    val syncNote: SyncNotesUseCase,
    val syncCategories: SyncCategoriesUseCase,
    val syncTasks: SyncTasksUseCase
)
