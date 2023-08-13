package com.ec25p5e.notesapp.feature_task.data.repository

import android.content.res.Resources
import android.util.Log
import androidx.compose.ui.res.stringResource
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.scheduler.AndroidAlarmScheduler
import com.ec25p5e.notesapp.core.domain.models.AlarmItem
import com.ec25p5e.notesapp.core.presentation.util.date
import com.ec25p5e.notesapp.core.presentation.util.time
import com.ec25p5e.notesapp.feature_task.data.data_source.TaskDao
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val alarmScheduler: AndroidAlarmScheduler
): TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override fun getTaskById(id: Int): Task {
        return dao.getTaskById(id)
    }

    override fun insertTask(task: Task): Long {
        val alarmItem = AlarmItem(
            alarmId = task.uid,
            time = LocalDateTime.parse(task.dueDateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            message = task.title
        )
        alarmItem.let(alarmScheduler::schedule)

        return dao.insertTask(task)
    }

    override fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}