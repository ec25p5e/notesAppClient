package com.ec25p5e.notesapp.feature_task.domain.use_cases.task

import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository

class AddTask(
    private val repository: TaskRepository
) {

    operator fun invoke(task: Task): Long {
        return repository.insertTask(task)
    }
}