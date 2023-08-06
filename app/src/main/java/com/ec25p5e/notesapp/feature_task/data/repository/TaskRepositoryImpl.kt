package com.ec25p5e.notesapp.feature_task.data.repository

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_task.data.data_source.TaskDao
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl(
    private val dao: TaskDao,
    dataStore: DataStorePreferenceImpl,
): TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override fun getTaskById(id: Int): Task {
        return dao.getTaskById(id)
    }

    override fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}