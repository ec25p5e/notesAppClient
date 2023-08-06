package com.ec25p5e.notesapp.feature_task.presentation.todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(TodoState())
    val state: State<TodoState> = _state


    val allTasks = mutableStateListOf<Task>()
    val tasksUpcoming = mutableStateListOf<Task>()
    val tasksRegular = mutableStateListOf<Task>()
    val tasksChecklist = mutableStateListOf<Task>()
    val tasksToday = mutableStateListOf<Task>()


}