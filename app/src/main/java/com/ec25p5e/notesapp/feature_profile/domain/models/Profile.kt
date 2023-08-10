package com.ec25p5e.notesapp.feature_profile.domain.models

data class Profile(
    val userId: String,
    val email: String,
    val username: String,
    val profilePictureUrl: String,
    val bannerUrl: String?,
)
