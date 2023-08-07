package com.ec25p5e.notesapp.feature_task.presentation.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.date
import com.ec25p5e.notesapp.core.presentation.util.daysBetween
import com.ec25p5e.notesapp.core.presentation.util.time
import com.ec25p5e.notesapp.core.presentation.util.today
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.TaskUseCases
import com.ec25p5e.notesapp.feature_task.presentation.util.UiEventTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventTask>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getTasksJob: Job? = null

    init {
        initTodaysTasks()
    }

    fun onEvent(event: TaskEvent) {
        when(event) {
            is TaskEvent.MarkTaskDone -> {
                markTaskDone(event.task)
            }
            is TaskEvent.DeleteTask -> {

            }
            is TaskEvent.EditTask -> {
                editTask(event.task)
            }
        }
    }

    private fun markTaskDone(task: Task) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isGreat = true
            )
            delay(3000)
            _state.value = _state.value.copy(
                isGreat = false
            )
        }
    }

    private fun editTask(task: Task) {
        viewModelScope.launch {
            _eventFlow.emit(UiEventTask.Navigate(Screen.AddEditTaskScreen.route + "?taskId=${task.id}"))
        }
    }

    private fun initTodaysTasks() {
        viewModelScope.launch {
            getTasksJob?.cancel()
            getTasksJob = taskUseCases.getTasks()
                .onEach { tasks ->
                    val todaysTasks = tasks.filter { it.dueDateTime.date == today }

                    val upcoming = tasks.filter {
                        it.dueDateTime.date.isNotEmpty() && ((daysBetween(it.dueDateTime.date, today)
                            ?: -1) > 0)
                    }

                    val regular = tasks.filter {
                        val dateTime = it.dueDateTime
                        val d = dateTime.date
                        val t = dateTime.time
                        d.isEmpty()&&t.isNotEmpty()
                    }

                    _state.value = _state.value.copy(
                        tasksToday = todaysTasks,
                        allTasks = tasks,
                        tasksUpcoming = upcoming,
                        tasksRegular = regular
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}