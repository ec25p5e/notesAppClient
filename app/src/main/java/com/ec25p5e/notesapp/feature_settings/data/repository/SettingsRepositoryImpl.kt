package com.ec25p5e.notesapp.feature_settings.data.repository

import android.content.SharedPreferences
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_settings.domain.models.Settings
import com.ec25p5e.notesapp.feature_settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
): SettingsRepository {

    override fun editSharedPref(settings: Settings) {
        sharedPreferences.edit()
            .putString(Constants.KEY_SETTINGS, settings.toString())
    }

    override fun deleteSharedPref() {
        TODO("Not yet implemented")
    }
}