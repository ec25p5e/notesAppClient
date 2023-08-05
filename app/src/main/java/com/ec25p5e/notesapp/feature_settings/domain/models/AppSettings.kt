package com.ec25p5e.notesapp.feature_settings.domain.models

import com.ec25p5e.notesapp.feature_settings.domain.models.Language
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH,
    val isAutoSaveEnabled: Boolean = false,
    val unlockMethod: UnlockMethod = UnlockMethod.NONE,
    val appTheme: AppTheme = AppTheme.NATURE,
    val isScreenshotEnabled: Boolean = true,
    val isSharingEnabled: Boolean = true
)