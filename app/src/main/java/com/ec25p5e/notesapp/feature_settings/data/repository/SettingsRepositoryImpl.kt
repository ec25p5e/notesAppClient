package com.ec25p5e.notesapp.feature_settings.data.repository

import com.ec25p5e.notesapp.core.data.util.PreferencesManager
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_settings.domain.models.Settings
import com.ec25p5e.notesapp.feature_settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val preferencesManager: PreferencesManager
): SettingsRepository {

    override fun editSharedPref(settings: Settings) {
        preferencesManager.put(settings, Constants.KEY_SETTINGS)
    }

    override fun getSharedPreferences(key: String): Settings {
        return preferencesManager.get<Settings>(key)!!
    }

    override fun deleteSharedPref() {
        TODO("Not yet implemented")
    }
}