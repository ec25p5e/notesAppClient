package com.ec25p5e.notesapp.core.data.local.datastore_pref

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {

    suspend fun<T> getPreference(key: Preferences.Key<T>, defaultValue : T): Flow<T>

    suspend fun <T> getFirstPreference(key: Preferences.Key<T>,defaultValue: T):T
    suspend fun<T> putPreference(key: Preferences.Key<T>, value: T)
    suspend fun<T> removePreference(key: Preferences.Key<T>)
    suspend fun clearAllPreference()
}