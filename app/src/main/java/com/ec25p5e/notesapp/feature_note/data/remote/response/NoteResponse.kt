package com.ec25p5e.notesapp.feature_note.data.remote.response

import com.ec25p5e.notesapp.feature_note.domain.model.Note

data class NoteResponse(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val isArchived: Boolean,
    val categoryId: Int,
) {

    fun toNote(): Note {
        return Note(
            id = id.toInt(),
            title = title,
            content = content,
            timestamp = timestamp,
            color = color,
            isArchived = isArchived,
            categoryId = categoryId
        )
    }
}
