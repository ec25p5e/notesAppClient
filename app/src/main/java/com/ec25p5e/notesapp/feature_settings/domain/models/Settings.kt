package com.ec25p5e.notesapp.feature_settings.domain.models

data class Settings(
    val isAutoSaveEnabled: Boolean,
    val unlockMethod: UnlockMethod? = null,
    val appTheme: AppTheme? = null,
    val isScreenshotEnabled: Boolean,
    val isSharingEnabled: Boolean
)