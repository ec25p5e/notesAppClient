package com.ec25p5e.notesapp.di

import android.app.Application
import androidx.room.Room
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
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
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.DeleteCategory
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.GetCategories
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.GetCategoryById
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.PushCategory
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
    fun provideCategoryApi(client: OkHttpClient): CategoryApi {
        return Retrofit.Builder()
            .baseUrl(CategoryApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        db: NoteDatabase,
        api: NoteApi,
        dataStore: DataStorePreferenceImpl
    ): NoteRepository {
        return NoteRepositoryImpl(db.noteDao, db.categoryDao, api, dataStore)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        db: NoteDatabase,
        api: CategoryApi,
        dataStore: DataStorePreferenceImpl
    ): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao, api, dataStore)
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
            getNotesByCategory = GetNoteByCategory(repository),
            dearchiveNote = DearchiveNote(repository),
            copyNote = CopyNote(repository),
            lockNote = LockNote(repository),
            unLockNote = UnLockNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(
        repository: CategoryRepository,
        noteRepository: NoteRepository,
        preferenceImpl: DataStorePreferenceImpl
    ): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(repository),
            addCategory = AddCategory(repository, preferenceImpl),
            deleteCategory = DeleteCategory(repository, noteRepository),
            getCategoryById = GetCategoryById(repository),
            pushCategory = PushCategory(repository)
        )
    }
}