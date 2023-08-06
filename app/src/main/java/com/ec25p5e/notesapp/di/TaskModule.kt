package com.ec25p5e.notesapp.di

import android.app.Application
import androidx.room.Room
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.local.encryption.CryptoManager
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDatabase
import com.ec25p5e.notesapp.feature_note.data.remote.api.CategoryApi
import com.ec25p5e.notesapp.feature_note.data.remote.api.NoteApi
import com.ec25p5e.notesapp.feature_note.data.repository.CategoryRepositoryImpl
import com.ec25p5e.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.AddCategory
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.CategoryUseCases
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.GetCategories
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.AddNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.ArchiveNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.CopyNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.DearchiveNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.DeleteNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNoteByCategory
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNotes
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNotesForArchive
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.LockNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.UnLockNote
import com.ec25p5e.notesapp.feature_task.data.data_source.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

}