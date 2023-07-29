package com.ec25p5e.notesapp.feature_auth.domain.models

import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)