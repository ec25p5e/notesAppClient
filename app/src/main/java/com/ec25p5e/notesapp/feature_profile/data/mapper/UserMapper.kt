package com.ec25p5e.notesapp.feature_profile.data.mapper

import com.ec25p5e.notesapp.feature_profile.data.dto.UserItemDto
import com.ec25p5e.notesapp.feature_profile.domain.models.UserItem

fun UserItemDto.toUserItem(): UserItem {
    return UserItem(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
        bio = bio,
        isFollowing = isFollowing
    )
}