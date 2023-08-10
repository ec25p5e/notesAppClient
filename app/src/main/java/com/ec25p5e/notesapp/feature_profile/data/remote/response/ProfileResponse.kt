package com.ec25p5e.notesapp.feature_profile.data.remote.response

data class ProfileResponse(
    val userId: String,
    val email: String,
    val username: String,
    val profilePictureUrl: String,
    val bannerUrl: String?
)
