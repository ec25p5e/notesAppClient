package com.ec25p5e.notesapp.feature_chat.data.remote.data

data class WsClientMessage(
    val toId: String,
    val text: String,
    val chatId: String?,
)