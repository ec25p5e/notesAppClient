package com.ec25p5e.notesapp.feature_task.domain.repository

import com.ec25p5e.notesapp.feature_task.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks(): Flow<List<Task>>

    fun getTaskById(id: Int): Task

    fun insertTask(task: Task): Long

    fun deleteTask(task: Task)


}