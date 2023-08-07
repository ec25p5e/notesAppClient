package com.ec25p5e.notesapp.feature_settings.presentation.settings

sealed class SettingsEvent {
    data object ToggleAutoSave : SettingsEvent()
    data object ToggleSharingMode: SettingsEvent()
}
