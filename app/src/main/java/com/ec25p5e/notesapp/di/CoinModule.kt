package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_crypto.data.remote.CoinApi
import com.ec25p5e.notesapp.feature_crypto.data.repository.CoinRepositoryImpl
import com.ec25p5e.notesapp.feature_crypto.domain.repository.CoinRepository
import com.ec25p5e.notesapp.feature_crypto.domain.use_case.CoinUseCases
import com.ec25p5e.notesapp.feature_crypto.domain.use_case.GetCoinUseCase
import com.ec25p5e.notesapp.feature_crypto.domain.use_case.GetCoinsUseCase
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): CoinApi {
        return Retrofit.Builder()
            .baseUrl(Constants.COIN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: CoinRepository): CoinUseCases {
        return CoinUseCases(
            getCoinsUseCase = GetCoinsUseCase(repository),
            getCoinUseCase = GetCoinUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}