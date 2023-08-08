package com.ec25p5e.notesapp.feature_profile.presentation.profile

import com.ec25p5e.notesapp.feature_profile.domain.models.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val isLogoutDialogVisible: Boolean = false
)