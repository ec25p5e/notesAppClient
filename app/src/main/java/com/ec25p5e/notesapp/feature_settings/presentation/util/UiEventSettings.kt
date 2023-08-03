package com.ec25p5e.notesapp.feature_settings.presentation.util

import com.ec25p5e.notesapp.core.util.Event
import com.ec25p5e.notesapp.core.util.UiText

sealed class UiEventSettings: Event() {
    data class ShowSnackbar(val uiText: UiText?): UiEventSettings()
    data class Navigate(val route: String): UiEventSettings()
    object ShowLoader: UiEventSettings()

    object NavigateUp: UiEventSettings()
}