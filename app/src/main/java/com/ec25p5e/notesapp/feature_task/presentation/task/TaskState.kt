package com.ec25p5e.notesapp.feature_task.presentation.task

import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task

data class TaskState(
    val isLoading: Boolean = false,
    val isGreat: Boolean = false,
    val checkablesForTask: List<Checkable> = emptyList(),
    val allTasks: List<Task>  = emptyList(),
    val tasksUpcoming: List<Task> = emptyList(),
    val tasksRegular: List<Task>  = emptyList(),
    val tasksChecklist: List<Task>  = emptyList(),
    val tasksToday: List<Task> = emptyList()
)
