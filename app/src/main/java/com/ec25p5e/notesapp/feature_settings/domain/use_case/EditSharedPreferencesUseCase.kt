package com.ec25p5e.notesapp.feature_settings.domain.use_case

import com.ec25p5e.notesapp.feature_settings.domain.models.Settings
import com.ec25p5e.notesapp.feature_settings.domain.repository.SettingsRepository

class EditSharedPreferencesUseCase(
    private val repository: SettingsRepository
) {

    operator fun invoke(settings: Settings) {
        repository.editSharedPref(settings)
    }
}