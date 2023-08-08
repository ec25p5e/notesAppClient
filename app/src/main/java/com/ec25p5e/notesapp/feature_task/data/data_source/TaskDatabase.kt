package com.ec25p5e.notesapp.feature_task.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.ec25p5e.notesapp.core.data.local.converters.ArrayListConverter
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task

@Database(
    entities = [Task::class, Checkable::class],
    version = 1,
    exportSchema = true,
    /*autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = TaskDatabase.Migration1To2::class)
    ] */
)
@TypeConverters(ArrayListConverter::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val checkableDao: CheckableDao

    /* @RenameColumn(tableName = "Task", fromColumnName = "created", toColumnName = "createdAt")
    @RenameColumn(tableName = "Task", fromColumnName = "updated", toColumnName = "updatedAt")
    class Migration1To2: AutoMigrationSpec */

    companion object {
        const val DATABASE_NAME = "task_db"
    }
}