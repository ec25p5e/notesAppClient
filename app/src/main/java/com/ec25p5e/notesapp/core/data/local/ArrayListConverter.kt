package com.ec25p5e.notesapp.core.data.local

import androidx.room.TypeConverter
import com.ec25p5e.notesapp.core.presentation.util.fromJson
import com.google.gson.Gson

class ArrayListConverter {

    @TypeConverter
    fun fromStringArrayList(value: ArrayList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}