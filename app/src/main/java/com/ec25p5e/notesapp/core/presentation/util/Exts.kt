package com.ec25p5e.notesapp.core.presentation.util

import com.google.common.reflect.TypeToken
import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)