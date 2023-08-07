package com.ec25p5e.notesapp.feature_settings.presentation.sync_remote

sealed class SyncEvent {
    data object ToggleNoteSync: SyncEvent()
    data object ToggleCategoriesSync: SyncEvent()
    data object ToggleTasksSync: SyncEvent()
    data object StartSync: SyncEvent()
}
