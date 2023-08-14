package com.ec25p5e.notesapp.feature_chat.data.remote.data

data class ChatDto(
    val chatId: String,
    val remoteUserId: String?,
    val remoteUsername: String?,
    val remoteUserProfilePictureUrl: String?,
    val lastMessage: String?,
    val timestamp: Long?
)
