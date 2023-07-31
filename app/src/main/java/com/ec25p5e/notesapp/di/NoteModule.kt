package com.ec25p5e.notesapp.di

import android.app.Application
import androidx.room.Room
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDatabase
import com.ec25p5e.notesapp.feature_note.data.repository.CategoryRepositoryImpl
import com.ec25p5e.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.CategoryUseCases
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.GetCategories
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.AddNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.ArchiveNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.DeleteNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNote
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNotes
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.GetNotesForArchive
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
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
            getNotesForArchive = GetNotesForArchive(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(repository)
        )
    }
}