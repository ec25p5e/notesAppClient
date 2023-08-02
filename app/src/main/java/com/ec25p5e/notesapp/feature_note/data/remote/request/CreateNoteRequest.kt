package com.ec25p5e.notesapp.feature_note.data.remote.request

data class CreateNoteRequest(
    val userId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val isArchived: Boolean,
    val idCategory: Int
) {

}
