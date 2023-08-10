package com.ec25p5e.notesapp.core.domain.models

data class User(
    val userId: String,
    val username: String,
    val email: String,
    val profilePictureUrl: String,
)