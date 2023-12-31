package com.ec25p5e.notesapp.core.data.local.datastore_pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStorePreferenceImpl @Inject constructor(
    dataStore: DataStore<Preferences>
) : DataStorePreferences {

    private val dataSource = dataStore

    override suspend fun <T> getPreference(key : Preferences.Key<T>, defaultValue : T) :
            Flow<T> = dataSource.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val result = preferences[key]?: defaultValue
        result
    }

    override suspend fun <T> getFirstPreference(key: Preferences.Key<T>, defaultValue: T) :
            T = dataSource.data.first()[key] ?: defaultValue
    override suspend fun<T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun<T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit {
            it.remove(key)
        }
    }

    override suspend fun clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }

}