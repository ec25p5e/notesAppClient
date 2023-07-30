package com.ec25p5e.notesapp.feature_chat.domain.model

data class Chat(
    val chatId: String,
    val remoteUserId: String,
    val remoteUsername: String,
    val remoteUserProfilePictureUrl: String,
    val lastMessage: String,
    val timestamp: Long
)
