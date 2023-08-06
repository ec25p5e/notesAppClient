package com.ec25p5e.notesapp.feature_task.domain.use_cases.task

data class TaskUseCases(
    val addTask: AddTask,
    val getTasks: GetTasks,
    val getTaskById: GetTaskById,
    val deleteTask: DeleteTask,
)
