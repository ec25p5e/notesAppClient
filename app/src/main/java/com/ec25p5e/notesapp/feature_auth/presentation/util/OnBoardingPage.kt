package com.ec25p5e.notesapp.feature_auth.presentation.util

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.ec25p5e.notesapp.R

sealed class OnBoardingPage(
    @RawRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
) {

    data object FeatureNote : OnBoardingPage(
        image = R.raw.write_notes,
        title = R.string.onboarding_note_feature,
        description = R.string.onboarding_note_description
    )

    data object TodoFeature : OnBoardingPage(
        image = R.raw.empty_tasks,
        title = R.string.onboarding_todo_feature,
        description = R.string.onboarding_todo_description
    )

    data object ProfileFeature : OnBoardingPage(
        image = R.raw.sync_feature,
        title = R.string.onboarding_profile_feature,
        description = R.string.onboarding_profile_feature_description
    )

    data object AllowPermission: OnBoardingPage(
        image = R.raw.allow_notifications,
        title = R.string.onboarding_allow_permission,
        description = R.string.onboarding_allow_permission_description
    )

}