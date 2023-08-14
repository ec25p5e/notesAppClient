package com.ec25p5e.notesapp.feature_chat.domain.use_case

import com.ec25p5e.notesapp.feature_chat.domain.model.Message
import com.ec25p5e.notesapp.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveMessages(
    private val repository: ChatRepository
) {

    operator fun invoke(): Flow<Message> {
        return repository.observeMessages()
    }

}