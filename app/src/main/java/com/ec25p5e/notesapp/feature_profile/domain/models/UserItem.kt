package com.ec25p5e.notesapp.feature_profile.domain.models

data class UserItem(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
)