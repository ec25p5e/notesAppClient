package com.ec25p5e.notesapp.feature_settings.presentation.settings

sealed class SettingsEvent {
    object ToggleAutoSave : SettingsEvent()
    object ToggleScreenShotMode: SettingsEvent()
    object ToggleSharingMode: SettingsEvent()
}
