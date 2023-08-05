package com.ec25p5e.notesapp.core.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferenceConstants {
    val USER_TOKEN = stringPreferencesKey("JWT_TOKEN")
    val USER_ID = stringPreferencesKey("USER_ID")
}