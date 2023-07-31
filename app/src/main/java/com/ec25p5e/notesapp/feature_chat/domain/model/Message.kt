package com.ec25p5e.notesapp.feature_chat.domain.model

data class Message(
    val fromId: String,
    val toId: String,
    val text: String,
    val formattedTime: String,
    val chatId: String?
)