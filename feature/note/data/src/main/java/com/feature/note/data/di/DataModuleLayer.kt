package com.feature.note.data.di

import android.app.Application
import androidx.room.Room
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDatabase
import com.ec25p5e.notesapp.feature_note.data.remote.api.CategoryApi
import com.ec25p5e.notesapp.feature_note.data.remote.api.NoteApi
import com.ec25p5e.notesapp.feature_note.data.repository.CategoryRepositoryImpl
import com.ec25p5e.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModuleLayer {

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
    fun provideNoteApi(): NoteApi {
        return Retrofit.Builder()
            .baseUrl(NoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(): CategoryApi {
        return Retrofit.Builder()
            .baseUrl(CategoryApi.BASE_URL)
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
        return CategoryRepositoryImpl(db.categoryDao, db.noteDao, api, dataStore)
    }
}