package com.ec25p5e.notesapp.feature_chat.domain.repository

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_chat.domain.model.Chat
import com.ec25p5e.notesapp.feature_chat.domain.model.Message
import kotlinx.coroutines.flow.Flow
import com.tinder.scarlet.WebSocket

interface ChatRepository {

    fun initialize()

    suspend fun getChatsForUser(): Resource<List<Chat>>

    suspend fun getMessagesForChat(chatId: String, page: Int, pageSize: Int): Resource<List<Message>>

    fun observeChatEvents(): Flow<WebSocket.Event>

    fun observeMessages(): Flow<Message>

    fun sendMessage(toId: String, text: String, chatId: String?)
}