package com.ec25p5e.notesapp.feature_auth.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ec25p5e.notesapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
) {

    object First : OnBoardingPage(
        image = R.drawable.ic_image,
        title = R.string.onboarding_note_feature,
        description = R.string.onboarding_note_description
    )

    object Second : OnBoardingPage(
        image = R.drawable.ic_save,
        title = R.string.onboarding_todo_feature,
        description = R.string.onboarding_todo_description
    )

}