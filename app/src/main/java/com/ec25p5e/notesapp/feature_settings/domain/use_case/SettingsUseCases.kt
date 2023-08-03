package com.ec25p5e.notesapp.feature_settings.domain.use_case

data class SettingsUseCases(
    val editSharedPreferences: EditSharedPreferencesUseCase,
    val deleteSharedPreferences: DeleteSharedPreferenceUseCase
)