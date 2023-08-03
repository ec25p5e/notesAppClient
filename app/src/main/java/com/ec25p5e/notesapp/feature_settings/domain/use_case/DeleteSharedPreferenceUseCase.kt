package com.ec25p5e.notesapp.feature_settings.domain.use_case

import com.ec25p5e.notesapp.feature_settings.domain.repository.SettingsRepository

class DeleteSharedPreferenceUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke() {

    }
}