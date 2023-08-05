package com.ec25p5e.notesapp.di

import android.content.SharedPreferences
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_profile.data.remote.ProfileApi
import com.ec25p5e.notesapp.feature_profile.data.repository.ProfileRepositoryImpl
import com.ec25p5e.notesapp.feature_profile.domain.repository.ProfileRepository
import com.ec25p5e.notesapp.feature_profile.domain.use_case.GetProfileUseCase
import com.ec25p5e.notesapp.feature_profile.domain.use_case.LogoutUseCase
import com.ec25p5e.notesapp.feature_profile.domain.use_case.ProfileUseCases
import com.google.gson.Gson
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
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileApi: ProfileApi,
        gson: Gson,
        dataStore: DataStorePreferenceImpl,
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileApi, gson,  dataStore)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            logout = LogoutUseCase(repository)
        )
    }
}