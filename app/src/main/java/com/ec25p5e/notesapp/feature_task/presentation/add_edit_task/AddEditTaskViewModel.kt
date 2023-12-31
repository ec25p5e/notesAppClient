package com.ec25p5e.notesapp.feature_task.presentation.add_edit_task

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.util.date
import com.ec25p5e.notesapp.core.presentation.util.formatted
import com.ec25p5e.notesapp.core.presentation.util.time
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable.CheckableUseCases
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.TaskUseCases
import com.ec25p5e.notesapp.feature_task.presentation.util.UiEventTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val checkableUseCases: CheckableUseCases,
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

    private val _eventFlow = MutableSharedFlow<UiEventTask>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var textToSpeech:TextToSpeech? = null

    var currentTaskId: Int? = null

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if(taskId != -1) {
                viewModelScope.launch {
                    taskUseCases.getTaskById(taskId)?.also { task ->
                        currentTaskId = task.id
                        _titleState.value = titleState.value.copy(
                            text = task.title,
                            isHintVisible = false
                        )
                        _contentState.value = _contentState.value.copy(
                            text = task.description,
                            isHintVisible = false
                        )
                        _colorState.value = task.color
                        _date.value = task.dueDateTime.date
                        _time.value = task.dueDateTime.time
                    }
                }
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
                onCheckableCheck(event.item)
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
                saveTask()
            }
            is AddEditTaskEvent.ReadTask -> {
                readTask(event.context)
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

    private fun onCheckableCheck(item: Checkable) {
        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }

        _checkables[index] = item.copy(checked = !_checkables[index].checked)
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
        _date.value = date.formatted
    }

    private fun onTimeDialogOk() {
        _timeDialogOpen.value = false
    }

    private fun onTimeDialogCancel() {
        _timeDialogOpen.value = false
    }

    private fun onTimeDialogTime(time: LocalTime) {
        _time.value = time.formatted
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

    private fun saveTask() {
        viewModelScope.launch {
            _eventFlow.emit(UiEventTask.ShowLoader)

            val taskId = taskUseCases.addTask(Task(
                title = _titleState.value.text,
                description = _contentState.value.text,
                dueDateTime = (_date.value + " " + _time.value).trim(),
                color = _colorState.value,
                done = false,
                uid = newId,
                timestamp = System.currentTimeMillis()
            ))

            checkables.map { checkable -> checkable.taskId = taskId.toInt() }
            checkableUseCases.addCheckableList(checkables)

            _eventFlow.emit(UiEventTask.ShowSnackbar(UiText.StringResource(id =
                if(currentTaskId == null) {
                    R.string.task_created_successfully
                } else {
                    R.string.task_updated_text
                }
            )))
        }
    }

    private fun readTask(context: Context) {
        textToSpeech = TextToSpeech(
            context
        ) {
            if(it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.ITALIAN
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        _titleState.value.text,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }

                Executors.newSingleThreadScheduledExecutor().schedule({
                    textToSpeech?.let { txtToSpeech ->
                        txtToSpeech.language = Locale.ITALIAN
                        txtToSpeech.setSpeechRate(1.0f)
                        txtToSpeech.speak(
                            _contentState.value.text,
                            TextToSpeech.QUEUE_ADD,
                            null,
                            null
                        )
                    }
                }, 3, TimeUnit.SECONDS)
            }
        }
    }
}