package com.ec25p5e.notesapp.feature_auth.domain.use_case

import com.ec25p5e.notesapp.feature_auth.domain.models.LoginResult
import com.ec25p5e.notesapp.feature_auth.domain.repository.AuthRepository
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): LoginResult {
        val emailError = if(email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if(password.isBlank()) AuthError.FieldEmpty else null

        if(emailError != null || passwordError != null)
            return LoginResult(emailError, passwordError)

        return LoginResult(
            result = repository.login(email, password)
        )
    }
}