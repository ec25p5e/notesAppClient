package com.ec25p5e.notesapp.feature_task.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ec25p5e.notesapp.core.data.local.converters.ArrayListConverter
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task

@Database(
    entities = [Task::class, Checkable::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ArrayListConverter::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "task_db"
    }
}