package com.ec25p5e.notesapp.core.domain.models

data class UserItem(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
)