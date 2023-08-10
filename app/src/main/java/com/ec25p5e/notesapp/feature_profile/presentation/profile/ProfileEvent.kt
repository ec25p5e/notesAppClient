package com.ec25p5e.notesapp.feature_profile.presentation.profile

sealed class ProfileEvent {

    data object ShowLogoutDialog: ProfileEvent()
}