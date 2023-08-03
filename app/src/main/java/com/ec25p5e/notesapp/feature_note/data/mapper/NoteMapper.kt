package com.ec25p5e.notesapp.feature_note.data.mapper

import com.ec25p5e.notesapp.feature_note.data.remote.response.NoteResponse
import com.ec25p5e.notesapp.feature_note.domain.models.Note

fun NoteResponse.toNote(): Note {
    return Note(
        title = title,
        content = content,
        timestamp = timestamp,
        color = color,
        isArchived = isArchived,
        categoryId = categoryId,
        remoteId = noteId, // Remote note ID,
        image = image
    )
}