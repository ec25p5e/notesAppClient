package com.ec25p5e.notesapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.ec25p5e.notesapp.user_preferences"
)

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        myUserPreferencesRepository: DataStorePreferences
    ): DataStorePreferences

    companion object {

        @Provides
        @Singleton
        fun provideUserDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.userDataStore
        }

        @Provides
        @Singleton
        fun provideUserDataStorePrefImpl(
            dataStore: DataStore<Preferences>
        ): DataStorePreferences {
            return DataStorePreferenceImpl(dataStore)
        }
    }
}