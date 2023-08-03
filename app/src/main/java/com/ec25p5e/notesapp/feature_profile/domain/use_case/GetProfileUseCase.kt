package com.ec25p5e.notesapp.feature_profile.domain.use_case

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_profile.domain.models.Profile
import com.ec25p5e.notesapp.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String): Resource<Profile> {
        return repository.getProfile(userId)
    }
}