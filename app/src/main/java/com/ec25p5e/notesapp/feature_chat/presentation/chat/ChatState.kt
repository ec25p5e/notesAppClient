package com.ec25p5e.notesapp.feature_chat.presentation.chat

import com.ec25p5e.notesapp.feature_chat.domain.model.Chat

data class ChatState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false
)
