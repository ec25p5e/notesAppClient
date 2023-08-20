package com.feature.note.domain.di

import com.feature.note.domain.repository.CategoryRepository
import com.feature.note.domain.repository.NoteRepository
import com.feature.note.domain.use_cases.category.AddCategory
import com.feature.note.domain.use_cases.category.CategoryUseCases
import com.feature.note.domain.use_cases.category.DeleteCategory
import com.feature.note.domain.use_cases.category.GetCategories
import com.feature.note.domain.use_cases.category.GetCategoryById
import com.feature.note.domain.use_cases.category.PushCategory
import com.feature.note.domain.use_cases.note.AddNote
import com.feature.note.domain.use_cases.note.ArchiveNote
import com.feature.note.domain.use_cases.note.CopyNote
import com.feature.note.domain.use_cases.note.DearchiveNote
import com.feature.note.domain.use_cases.note.DeleteNote
import com.feature.note.domain.use_cases.note.GetNote
import com.feature.note.domain.use_cases.note.GetNoteByCategory
import com.feature.note.domain.use_cases.note.GetNotes
import com.feature.note.domain.use_cases.note.GetNotesForArchive
import com.feature.note.domain.use_cases.note.LockNote
import com.feature.note.domain.use_cases.note.NoteUseCases
import com.feature.note.domain.use_cases.note.UnLockNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModuleLayer {

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
    ): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(repository),
            addCategory = AddCategory(repository),
            deleteCategory = DeleteCategory(repository, noteRepository),
            getCategoryById = GetCategoryById(repository),
            pushCategory = PushCategory(repository)
        )
    }
}