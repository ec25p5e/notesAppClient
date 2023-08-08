package com.ec25p5e.notesapp.feature_task.presentation.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.util.date
import com.ec25p5e.notesapp.core.presentation.util.daysBetween
import com.ec25p5e.notesapp.core.presentation.util.time
import com.ec25p5e.notesapp.core.presentation.util.today
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable.CheckableUseCases
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.TaskUseCases
import com.ec25p5e.notesapp.feature_task.presentation.util.UiEventTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val checkableUseCases: CheckableUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventTask>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getTasksJob: Job? = null
    private var recentlyDeletedTask: Task? = null

    init {
        initTodaysTasks()
    }

    fun onEvent(event: TaskEvent) {
        when(event) {
            is TaskEvent.MarkTaskDone -> {
                markTaskDone(event.task)
            }
            is TaskEvent.DeleteTask -> {
                if(_state.value.taskToDelete != null) {
                    viewModelScope.launch {
                        taskUseCases.deleteTask(_state.value.taskToDelete!!)
                        recentlyDeletedTask = state.value.taskToDelete!!

                        _state.value = _state.value.copy(
                            isDeleting = false,
                            taskToDelete = null,
                        )

                        _eventFlow.emit(UiEventTask.ShowSnackbar(UiText.StringResource(id = R.string.deleted_task)))
                    }
                }
            }
            is TaskEvent.IsDeletingNote -> {
                _state.value = _state.value.copy(
                    isDeleting = !_state.value.isDeleting,
                    taskToDelete = null,
                )
            }
            is TaskEvent.SetTaskToDelete -> {
                _state.value = _state.value.copy(
                    taskToDelete = event.task,
                    isDeleting = true,
                )
            }
            is TaskEvent.EditTask -> {
                editTask(event.task)
            }
            is TaskEvent.GetCheckablesForTask -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        checkablesForTask = checkableUseCases.getCheckableByTask(event.taskId)
                    )
                }
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

    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()
}