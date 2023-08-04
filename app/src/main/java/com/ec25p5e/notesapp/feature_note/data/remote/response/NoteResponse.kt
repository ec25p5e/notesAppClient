package com.ec25p5e.notesapp.feature_note.data.remote.response

import com.ec25p5e.notesapp.feature_note.domain.models.Note

data class NoteResponse(
    val id: String,
    val noteId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val isArchived: Boolean,
    val categoryId: Int,
    val image: ArrayList<String>,
    val background: Int,
)
