package com.ec25p5e.notesapp.core.data.util

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class PreferencesManager(
    var sharedPreferences: SharedPreferences,
    var gson: Gson,
) {

    fun <T> put(`object`: T, key: String) {
        val jsonString = gson.toJson(`object`)

        sharedPreferences.edit()
            .putString(key, jsonString)
            .apply()

    }

    inline fun <reified T> get(key: String): T? {
        val value = sharedPreferences.getString(key, null)
        return gson.fromJson(value,T::class.java)
    }
}