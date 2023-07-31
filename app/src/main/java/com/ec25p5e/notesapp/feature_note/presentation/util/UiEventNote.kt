package com.ec25p5e.notesapp.feature_note.presentation.util

import com.ec25p5e.notesapp.core.util.Event
import com.ec25p5e.notesapp.core.util.UiText

sealed class UiEventNote: Event() {
    data class ShowSnackbar(val uiText: UiText): UiEventNote()
    data class Navigate(val route: String): UiEventNote()

    object NavigateUp: UiEventNote()
}