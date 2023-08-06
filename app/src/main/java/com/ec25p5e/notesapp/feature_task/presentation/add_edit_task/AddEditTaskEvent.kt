package com.ec25p5e.notesapp.feature_task.presentation.add_edit_task

import androidx.compose.ui.focus.FocusState
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import java.time.LocalDate
import java.time.LocalTime

sealed class AddEditTaskEvent {
    data class EnteredTitle(val value: String): AddEditTaskEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTaskEvent()
    data class EnteredContent(val value: String): AddEditTaskEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditTaskEvent()
    data class ChangeColor(val color: Int): AddEditTaskEvent()
    data class AddCheckable(val item: Checkable? = null): AddEditTaskEvent()
    data class DeleteCheckable(val item: Checkable): AddEditTaskEvent()
    data class CheckableCheck(val item: Checkable, val checked: Boolean): AddEditTaskEvent()
    data class CheckableValueChange(val item: Checkable, val value: String): AddEditTaskEvent()
    data class BackOnValue(val item: Checkable, val currentPos: Int, val previousPos: Int): AddEditTaskEvent()
    data class FocusGot(val item: Checkable): AddEditTaskEvent()
    data class DateDialogDate(val date: LocalDate): AddEditTaskEvent()
    data class TimeDialogDate(val time: LocalTime): AddEditTaskEvent()

    data object SaveTask: AddEditTaskEvent()
    data object DateClick: AddEditTaskEvent()
    data object TimeClick: AddEditTaskEvent()
    data object DateDialogOk: AddEditTaskEvent()
    data object DateDialogCancel: AddEditTaskEvent()
    data object DateDialogClear: AddEditTaskEvent()
    data object TimeDialogOk: AddEditTaskEvent()
    data object TimeDialogCancel: AddEditTaskEvent()
    data object TimeDialogClear: AddEditTaskEvent()
}
