package com.ec25p5e.notesapp.feature_profile.data.mapper

import com.ec25p5e.notesapp.feature_profile.data.remote.response.ProfileResponse
import com.ec25p5e.notesapp.feature_profile.domain.models.Profile

fun ProfileResponse.toProfile(): Profile {
    return Profile(
        userId = userId,
        email = email,
        username = username,
    )
}