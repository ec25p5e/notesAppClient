package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.util.PreferencesManager
import com.ec25p5e.notesapp.feature_auth.data.remote.AuthApi
import com.ec25p5e.notesapp.feature_auth.data.repository.AuthRepositoryImpl
import com.ec25p5e.notesapp.feature_auth.domain.repository.AuthRepository
import com.ec25p5e.notesapp.feature_auth.domain.use_case.AuthenticateUseCase
import com.ec25p5e.notesapp.feature_auth.domain.use_case.LoginUseCase
import com.ec25p5e.notesapp.feature_auth.domain.use_case.RegisterUseCase
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
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
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        preferencesManager: PreferencesManager,
        dataStore: DataStorePreferenceImpl,
        categoryRepository: CategoryRepository,
        noteRepository: NoteRepository
    ): AuthRepository {
        return AuthRepositoryImpl(
            api,
            preferencesManager,
            dataStore,
            categoryRepository,
            noteRepository
        )
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(
        repository: AuthRepository
    ): AuthenticateUseCase {
        return AuthenticateUseCase(repository)
    }
}