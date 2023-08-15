package com.ec25p5e.notesapp.feature_profile.domain.repository

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_profile.domain.models.Profile
import com.ec25p5e.notesapp.feature_profile.domain.models.UserItem

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun searchUser(query: String): Resource<List<UserItem>>

    fun logout()
}