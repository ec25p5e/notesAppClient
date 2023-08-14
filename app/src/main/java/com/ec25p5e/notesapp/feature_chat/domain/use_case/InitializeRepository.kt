package com.ec25p5e.notesapp.feature_chat.domain.use_case

import com.ec25p5e.notesapp.feature_chat.domain.repository.ChatRepository

class InitializeRepository(
    private val repository: ChatRepository
) {

    operator fun invoke() {
        repository.initialize()
    }
}