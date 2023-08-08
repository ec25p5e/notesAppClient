package com.ec25p5e.notesapp.feature_task.presentation.task

import com.ec25p5e.notesapp.feature_task.domain.models.Task

sealed class TaskEvent {

    data class MarkTaskDone(val task: Task): TaskEvent()
    data class SetTaskToDelete(val task: Task): TaskEvent()
    data class EditTask(val task: Task): TaskEvent()
    data class GetCheckablesForTask(val taskId: Int): TaskEvent()

    data object DeleteTask: TaskEvent()
    data object IsDeletingNote: TaskEvent()
}