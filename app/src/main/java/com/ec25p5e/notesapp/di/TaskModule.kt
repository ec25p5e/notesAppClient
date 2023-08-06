package com.ec25p5e.notesapp.di

import android.app.Application
import androidx.room.Room
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_task.data.data_source.TaskDatabase
import com.ec25p5e.notesapp.feature_task.data.repository.CheckableRepositoryImpl
import com.ec25p5e.notesapp.feature_task.data.repository.TaskRepositoryImpl
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.repository.CheckableRepository
import com.ec25p5e.notesapp.feature_task.domain.repository.TaskRepository
import com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable.AddCheckable
import com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable.CheckableUseCases
import com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable.GetCheckableByTask
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.AddTask
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.DeleteTask
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.GetTaskById
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.GetTasks
import com.ec25p5e.notesapp.feature_task.domain.use_cases.task.TaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        db: TaskDatabase,
        dataStore: DataStorePreferenceImpl
    ): TaskRepository {
        return TaskRepositoryImpl(db.taskDao, dataStore)
    }

    @Provides
    @Singleton
    fun provideCheckableRepository(
        db: TaskDatabase,
    ): CheckableRepository {
        return CheckableRepositoryImpl(db.checkableDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTaskById = GetTaskById(repository),
            getTasks = GetTasks(repository),
            addTask = AddTask(repository),
            deleteTask = DeleteTask(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCheckableUseCases(repository: CheckableRepository): CheckableUseCases {
        return CheckableUseCases(
            addCheckable = AddCheckable(repository),
            getCheckableByTask = GetCheckableByTask(repository)
        )
    }
}