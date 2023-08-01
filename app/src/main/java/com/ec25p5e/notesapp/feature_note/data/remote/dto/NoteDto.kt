package com.ec25p5e.notesapp.feature_note.data.remote.dto

import com.squareup.moshi.Json
data class NoteDto(
    @field:Json(name = "userId") val userId: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "content") val content: String?,
    @field:Json(name = "timestamp") val timestamp: Long?,
    @field:Json(name = "color") val color: Int?,
    @field:Json(name = "isArchived") val isArchived: Boolean?,
)
