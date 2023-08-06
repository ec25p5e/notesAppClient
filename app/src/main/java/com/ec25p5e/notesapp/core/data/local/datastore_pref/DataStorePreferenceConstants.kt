package com.ec25p5e.notesapp.core.data.local.datastore_pref

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferenceConstants {
    val USER_TOKEN = stringPreferencesKey("JWT_TOKEN")
    val USER_ID = stringPreferencesKey("USER_ID")
}