package com.ec25p5e.notesapp.feature_profile.domain.use_case

import com.ec25p5e.notesapp.feature_profile.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {

    operator fun invoke() {
        repository.logout()
    }
}