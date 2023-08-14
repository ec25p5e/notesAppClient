package com.ec25p5e.notesapp.di

import android.content.Context
import com.ec25p5e.notesapp.feature_chat.data.remote.ChatApi
import com.ec25p5e.notesapp.feature_chat.data.repository.ChatRepositoryImpl
import com.ec25p5e.notesapp.feature_chat.domain.repository.ChatRepository
import com.ec25p5e.notesapp.feature_chat.domain.use_case.ChatUseCases
import com.ec25p5e.notesapp.feature_chat.domain.use_case.GetChatsForUser
import com.ec25p5e.notesapp.feature_chat.domain.use_case.GetMessagesForChat
import com.ec25p5e.notesapp.feature_chat.domain.use_case.InitializeRepository
import com.ec25p5e.notesapp.feature_chat.domain.use_case.ObserveChatEvents
import com.ec25p5e.notesapp.feature_chat.domain.use_case.ObserveMessages
import com.ec25p5e.notesapp.feature_chat.domain.use_case.SendMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(repository),
            observeChatEvents = ObserveChatEvents(repository),
            observeMessages = ObserveMessages(repository),
            getChatsForUser = GetChatsForUser(repository),
            getMessagesForChat = GetMessagesForChat(repository),
            initializeRepository = InitializeRepository(repository)
        )
    }

    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController {
        return AndroidBluetoothController(context)
    }

    @Provides
    @Singleton
    fun provideChatApi(client: OkHttpClient): ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    fun provideChatRepository(client: OkHttpClient, chatApi: ChatApi): ChatRepository {
        return ChatRepositoryImpl(chatApi, client)
    }
}