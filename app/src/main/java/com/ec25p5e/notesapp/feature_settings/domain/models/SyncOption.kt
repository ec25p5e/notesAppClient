package com.ec25p5e.notesapp.feature_settings.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SyncOption(
    val isSyncNotes: Boolean = false,
    val isSyncCategories: Boolean = false,
    val isSyncTasks: Boolean = false,
)