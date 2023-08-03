package com.ec25p5e.notesapp.feature_settings.domain.models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Settings(
    val isAutoSaveEnabled: Boolean = false,
    val unlockMethod: UnlockMethod? = null,
    val appTheme: AppTheme? = null
)