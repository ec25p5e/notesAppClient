package com.ec25p5e.notesapp.feature_profile.domain.repository

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_profile.domain.models.Profile

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    fun logout()
}