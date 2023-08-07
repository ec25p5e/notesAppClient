package com.ec25p5e.notesapp.feature_auth.presentation.onboarding

sealed class OnBoardingEvent {
    data class PermissionResult(
        val permission: String,
        val isGranted: Boolean
    ): OnBoardingEvent()

    data class SaveOnBoarding(val isCompleted: Boolean): OnBoardingEvent()

    data object DismissDialog: OnBoardingEvent()
}
