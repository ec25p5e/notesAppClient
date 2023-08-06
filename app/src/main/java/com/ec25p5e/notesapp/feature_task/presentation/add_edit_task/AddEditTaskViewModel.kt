package com.ec25p5e.notesapp.feature_task.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private var dataStore: DataStore<AppSettings>,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState: State<StandardTextFieldState> = _titleState

    private val _contentState = mutableStateOf(StandardTextFieldState())
    val contentState: State<StandardTextFieldState> = _contentState

    private val _colorState = mutableStateOf(Task.taskColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _date = mutableStateOf("")
    val date: State<String> = _date

    private val _dateDialogOpen = mutableStateOf(false)
    val dateDialogOpen: State<Boolean> = _dateDialogOpen

    private val _timeDialogOpen = mutableStateOf(false)
    val timeDialogOpen: State<Boolean> = _timeDialogOpen

    private val _state = mutableStateOf(AddEditTaskState(
        isAutoSaveEnabled = runBlocking { dataStore.data.first().isAutoSaveEnabled },
        isSharing = runBlocking { dataStore.data.first().isSharingEnabled },
    ))
    val state: State<AddEditTaskState> = _state

    private val _currentFocusRequestId = mutableStateOf(-1L)
    val currentFocusRequestId: State<Long> = _currentFocusRequestId

    private val newId: Long
        get() {
            return System.currentTimeMillis()
        }

    private val _checkables = mutableStateListOf<Checkable>()
    val checkables: SnapshotStateList<Checkable> = _checkables

    private val _time = mutableStateOf("")
    val time: State<String> = _time

    var currentTaskId: Int? = null

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            viewModelScope.launch {

            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when(event) {
            is AddEditTaskEvent.EnteredTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeTitleFocus -> {
                _titleState.value = titleState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            titleState.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.EnteredContent -> {
                _contentState.value = _contentState.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeContentFocus -> {
                _contentState.value = _contentState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _contentState.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.AddCheckable -> {
                onAddCheckable()
            }
            is AddEditTaskEvent.DeleteCheckable -> {
                onCheckableDelete(event.item)
            }
            is AddEditTaskEvent.CheckableCheck -> {
                onCheckableCheck(event.item, event.checked)
            }
            is AddEditTaskEvent.CheckableValueChange -> {
                onCheckableValueChange(event.item, event.value)
            }
            is AddEditTaskEvent.BackOnValue -> {
                onBackOnValue(event.item, event.currentPos, event.previousPos)
            }
            is AddEditTaskEvent.FocusGot -> {
                onFocusGot(event.item)
            }
            is AddEditTaskEvent.DateClick -> {
                onDateClick()
            }
            is AddEditTaskEvent.DateDialogOk -> {
                onDateDialogOk()
            }
            is AddEditTaskEvent.DateDialogCancel -> {
                onDateDialogCancel()
            }
            is AddEditTaskEvent.DateDialogClear -> {
                onDateDialogClear()
            }
            is AddEditTaskEvent.DateDialogDate -> {
                onDateDialogDate(event.date)
            }
            is AddEditTaskEvent.TimeDialogOk -> {
                onTimeDialogOk()
            }
            is AddEditTaskEvent.TimeDialogCancel -> {
                onTimeDialogCancel()
            }
            is AddEditTaskEvent.TimeDialogClear -> {
                onTimeDialogClear()
            }
            is AddEditTaskEvent.TimeDialogDate -> {
                onTimeDialogTime(event.time)
            }
            is AddEditTaskEvent.TimeClick -> {
                onTimeClick()
            }
            is AddEditTaskEvent.ChangeColor -> {
                _colorState.value = event.color
            }
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {

                }
            }
        }
    }

    private fun onAddCheckable(item: Checkable? = null) {
        val id = newId
        val checkable = Checkable(
            uid = id
        )

        _currentFocusRequestId.value = id

        if(item==null){
            _checkables.add(checkable)
            return
        }

        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }

        if(index > -1){
            _checkables.add(index+1,checkable)
        } else{
            _checkables.add(checkable)
        }
    }

    private fun onCheckableDelete(item: Checkable) {
        _checkables.remove(item)
    }

    private fun onCheckableCheck(item: Checkable, checked: Boolean) {
        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }
        _checkables[index] = item.copy(checked = checked)
    }

    private fun onCheckableValueChange(item: Checkable, value: String) {
        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }
        _checkables[index] = item.copy(value = value)
    }

    private fun onBackOnValue(item: Checkable, currentPos: Int, previousPos: Int) {
        if(currentPos == previousPos&&currentPos == 0) {
            val index = _checkables.indexOfFirst {
                item.uid == it.uid
            }

            if (item.value.isEmpty()) {
                onCheckableDelete(item)
            }

            if (index > 0) {
                _currentFocusRequestId.value = _checkables[index - 1].uid
            }
        }
    }

    private fun onFocusGot(item: Checkable) {
        _currentFocusRequestId.value = item.uid
    }

    private fun onDateClick() {
        _dateDialogOpen.value = true
    }

    private fun onDateDialogCancel() {
        _dateDialogOpen.value = false
    }

    private fun onDateDialogOk() {
        _dateDialogOpen.value = false
    }

    private fun onDateDialogDate(date: LocalDate) {
        _date.value = date.toString()
    }

    private fun onTimeDialogOk() {
        _timeDialogOpen.value = false
    }

    private fun onTimeDialogCancel() {
        _timeDialogOpen.value = false
    }

    private fun onTimeDialogTime(time: LocalTime) {
        _time.value = time.toString()
    }

    private fun onDateDialogClear() {
        _dateDialogOpen.value = false
        _date.value = ""
    }

    private fun onTimeDialogClear() {
        _timeDialogOpen.value = false
        _time.value = ""
    }

    private fun onTimeClick() {
        _timeDialogOpen.value = true
    }
}