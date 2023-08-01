package com.ec25p5e.notesapp.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.ec25p5e.notesapp.feature_auth.data.remote.AuthApi
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDatabase
import com.ec25p5e.notesapp.feature_note.data.remote.NoteApi
import com.ec25p5e.notesapp.feature_note.data.repository.CategoryRepositoryImpl
import com.ec25p5e.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.AddCategory
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.CategoryUseCases
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.GetCategories
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.AddNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.ArchiveNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.DeleteNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNoteByCategory
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNotes
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNotesForArchive
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
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
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteApi(client: OkHttpClient): NoteApi {
        return Retrofit.Builder()
            .baseUrl(NoteApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase,
                              api: NoteApi,
                              sharedPreferences: SharedPreferences): NoteRepository {
        return NoteRepositoryImpl(db.noteDao, api, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: NoteDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
            archiveNote = ArchiveNote(repository),
            getNotesForArchive = GetNotesForArchive(repository),
            getNotesByCategory = GetNoteByCategory(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(repository),
            addCategory = AddCategory(repository)
        )
    }
}