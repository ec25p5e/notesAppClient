package com.ec25p5e.notesapp.feature_task.presentation.util

import com.ec25p5e.notesapp.core.util.Event
import com.ec25p5e.notesapp.core.util.UiText

sealed class UiEventTask: Event() {
    data class ShowSnackbar(val uiText: UiText?): UiEventTask()
    data class Navigate(val route: String): UiEventTask()
    data object ShowLoader: UiEventTask()

    data object NavigateUp: UiEventTask()
    data object SaveNote: UiEventTask()
    data object IsLoadingPage: UiEventTask()
}