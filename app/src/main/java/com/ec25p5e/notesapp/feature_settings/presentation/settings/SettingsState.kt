package com.ec25p5e.notesapp.feature_settings.presentation.settings

import androidx.datastore.core.DataStore
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings

data class SettingsState(
    val settings: DataStore<AppSettings>,
    val isLoading: Boolean = false,
    val pageLoading: Screen? = null
)
