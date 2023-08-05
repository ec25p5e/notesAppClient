package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_settings.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton