package com.ec25p5e.notesapp.feature_chat.data.remote.data

data class MessageDto(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
    val id: String
)