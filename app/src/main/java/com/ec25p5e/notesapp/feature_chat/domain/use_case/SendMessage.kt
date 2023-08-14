package com.ec25p5e.notesapp.feature_chat.domain.use_case

import com.ec25p5e.notesapp.feature_chat.domain.repository.ChatRepository

class SendMessage(
    private val repository: ChatRepository
) {

    operator fun invoke(toId: String, text: String, chatId: String?) {
        if(text.isBlank()) {
            return
        }
        repository.sendMessage(toId, text.trim(), chatId)
    }
}