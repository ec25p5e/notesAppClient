package com.ec25p5e.notesapp.feature_chat.domain.use_case

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_chat.domain.model.Chat
import com.ec25p5e.notesapp.feature_chat.domain.repository.ChatRepository
class GetChatsForUser(
    private val repository: ChatRepository
) {

    suspend operator fun invoke(): Resource<List<Chat>> {
        return repository.getChatsForUser()
    }
}