package com.ec25p5e.notesapp.di

import android.content.SharedPreferences
import com.ec25p5e.notesapp.core.data.util.PreferencesManager
import com.ec25p5e.notesapp.feature_settings.data.repository.SettingsRepositoryImpl
import com.ec25p5e.notesapp.feature_settings.domain.repository.SettingsRepository
import com.ec25p5e.notesapp.feature_settings.domain.use_case.DeleteSharedPreferenceUseCase
import com.ec25p5e.notesapp.feature_settings.domain.use_case.EditSharedPreferencesUseCase
import com.ec25p5e.notesapp.feature_settings.domain.use_case.GetSharedPreferencesUseCase
import com.ec25p5e.notesapp.feature_settings.domain.use_case.SettingsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(preferencesManager: PreferencesManager): SettingsRepository {
        return SettingsRepositoryImpl(preferencesManager)
    }

    @Provides
    @Singleton
    fun provideSettingsUseCases(repository: SettingsRepository): SettingsUseCases {
        return SettingsUseCases(
            editSharedPreferences = EditSharedPreferencesUseCase(repository),
            deleteSharedPreferences = DeleteSharedPreferenceUseCase(repository),
            getSharedPreferences = GetSharedPreferencesUseCase(repository)
        )
    }
}