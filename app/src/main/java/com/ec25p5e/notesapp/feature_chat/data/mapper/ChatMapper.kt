package com.ec25p5e.notesapp.feature_chat.data.mapper

import com.ec25p5e.notesapp.feature_chat.data.remote.data.ChatDto
import com.ec25p5e.notesapp.feature_chat.domain.model.Chat

fun ChatDto.toChat(): Chat? {
    return Chat(
        chatId = chatId,
        remoteUserId = remoteUserId ?: return null,
        remoteUsername = remoteUsername ?: return null,
        remoteUserProfilePictureUrl = remoteUserProfilePictureUrl ?: return null,
        lastMessage = lastMessage ?: return null,
        timestamp = timestamp ?: return null
    )
}