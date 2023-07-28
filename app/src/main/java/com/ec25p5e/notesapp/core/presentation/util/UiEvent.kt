package com.ec25p5e.notesapp.core.presentation.util

import com.ec25p5e.notesapp.core.util.Event
import com.ec25p5e.notesapp.core.util.UiText

sealed class UiEvent: Event() {
    data class ShowSnackbar(val uiText: UiText): UiEvent()
    data class Navigate(val route: String): UiEvent()

    object NavigateUp: UiEvent()
    object OnLogin: UiEvent()
}