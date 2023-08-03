package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.feature_note.data.csv.CSVParser
import com.ec25p5e.notesapp.feature_note.data.csv.NoteParser
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteParserModule {

    @Binds
    @Singleton
    abstract fun bindNoteParser(noteParser: NoteParser): CSVParser<Note>
}