package com.ec25p5e.notesapp.core.data.local.converters

import androidx.compose.ui.graphics.Path
import androidx.room.TypeConverter
import com.ec25p5e.notesapp.core.presentation.util.fromJson
import com.ec25p5e.notesapp.feature_note.domain.models.PathProperties
import com.google.gson.Gson

class ListPairConverter {

    @TypeConverter
    fun fromStringListPair(value: List<Pair<Path, PathProperties>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringPairList(value: String): List<Pair<Path, PathProperties>>? {
        return try {
            Gson().fromJson<List<Pair<Path, PathProperties>>>(value)
        } catch(e: Exception) {
          null
        }
    }
}