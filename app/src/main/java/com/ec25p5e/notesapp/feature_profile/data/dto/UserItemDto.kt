package com.ec25p5e.notesapp.feature_profile.data.dto

import com.ec25p5e.notesapp.feature_profile.domain.models.UserItem

data class UserItemDto(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
)