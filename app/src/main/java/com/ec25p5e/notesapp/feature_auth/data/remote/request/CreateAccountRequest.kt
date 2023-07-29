package com.ec25p5e.notesapp.feature_auth.data.remote.request

data class CreateAccountRequest(
    val email: String,
    val username: String,
    val password: String
)