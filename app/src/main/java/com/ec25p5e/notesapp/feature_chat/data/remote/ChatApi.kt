package com.ec25p5e.notesapp.feature_chat.data.remote

import androidx.compose.foundation.pager.PageSize
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_chat.data.remote.data.ChatDto
import com.ec25p5e.notesapp.feature_chat.data.remote.data.MessageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApi {

    @GET("/api/chats")
    suspend fun getChatsForUser(): List<ChatDto>

    @GET("/api/chat/messages")
    suspend fun getMessagesForChat(
        @Query("chatId") chatId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<MessageDto>

    companion object {
        const val BASE_URL = Constants.BASE_URL_SERVER
    }
}