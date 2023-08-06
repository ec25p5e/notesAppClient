package com.ec25p5e.notesapp.feature_task.domain.use_cases.task

import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val repository: TaskRepository
) {

    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}