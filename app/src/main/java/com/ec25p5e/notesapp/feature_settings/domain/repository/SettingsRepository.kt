package com.ec25p5e.notesapp.feature_settings.domain.repository

import com.ec25p5e.notesapp.feature_settings.domain.models.Settings

interface SettingsRepository {

    fun editSharedPref(settings: Settings)

    fun getSharedPreferences(key: String): Settings

    fun deleteSharedPref()
}