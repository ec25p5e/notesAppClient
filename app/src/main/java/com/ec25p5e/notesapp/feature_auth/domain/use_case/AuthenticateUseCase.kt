package com.ec25p5e.notesapp.feature_auth.domain.use_case

import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_auth.domain.repository.AuthRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class AuthenticateUseCase(
    private val repository: AuthRepository,
    private val noteRepository: NoteRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): SimpleResource {
        val authResponse = repository.authenticate()

        if(authResponse.uiText == null) {
            noteRepository.pushNotes()
            categoryRepository.getAllCategories(true)
            noteRepository.getAllNotes(true)
        }

        return authResponse
    }
}