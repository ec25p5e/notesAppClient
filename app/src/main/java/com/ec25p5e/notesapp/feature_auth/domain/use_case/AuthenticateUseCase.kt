package com.ec25p5e.notesapp.feature_auth.domain.use_case

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_auth.domain.repository.AuthRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class AuthenticateUseCase(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}