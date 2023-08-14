package com.ec25p5e.notesapp.feature_chat.data.repository

import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.di.ScarletInstance
import com.ec25p5e.notesapp.feature_chat.data.mapper.toChat
import com.ec25p5e.notesapp.feature_chat.data.mapper.toMessage
import com.ec25p5e.notesapp.feature_chat.data.remote.ChatApi
import com.ec25p5e.notesapp.feature_chat.data.remote.ChatService
import com.ec25p5e.notesapp.feature_chat.data.remote.data.WsClientMessage
import com.ec25p5e.notesapp.feature_chat.domain.model.Chat
import com.ec25p5e.notesapp.feature_chat.domain.model.Message
import com.ec25p5e.notesapp.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import retrofit2.HttpException
import java.io.IOException

class ChatRepositoryImpl(
    private val chatApi: ChatApi,
    private val okHttpClient: OkHttpClient
): ChatRepository {

    private var chatService: ChatService? = null

    override fun initialize() {
        chatService = ScarletInstance.getNewInstance(okHttpClient)
    }

    override suspend fun getChatsForUser(): Resource<List<Chat>> {
        return try {
            val chats = chatApi
                .getChatsForUser()
                .mapNotNull { it.toChat() }
            Resource.Success(data = chats)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getMessagesForChat(chatId: String, page: Int, pageSize: Int): Resource<List<Message>> {
        return try {
            val messages = chatApi
                .getMessagesForChat(chatId, page, pageSize)
                .map { it.toMessage() }
            Resource.Success(data = messages)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override fun observeChatEvents(): Flow<WebSocket.Event> {
        return chatService?.observeEvents()
            ?.receiveAsFlow() ?: flow {  }
    }

    override fun observeMessages(): Flow<Message> {
        return chatService
            ?.observeMessages()
            ?.receiveAsFlow()
            ?.map { it.toMessage() } ?: flow {  }
    }

    override fun sendMessage(toId: String, text: String, chatId: String?) {
        chatService?.sendMessage(
            WsClientMessage(
                toId = toId,
                text = text,
                chatId = chatId
            )
        )
    }
}