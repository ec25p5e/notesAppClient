package com.ec25p5e.notesapp.feature_settings.presentation.settings

import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_settings.domain.models.Settings

data class SettingsState(
    val settings: Settings? = null,
    val isLoading: Boolean = false,
    val pageLoading: Screen? = null
)
