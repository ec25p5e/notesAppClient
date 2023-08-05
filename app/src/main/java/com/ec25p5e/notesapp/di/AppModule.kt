package com.ec25p5e.notesapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import coil.ImageLoader
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceConstants.USER_TOKEN
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.local.serializer.AppSettingsSerializer
import com.ec25p5e.notesapp.core.data.util.CryptoManager
import com.ec25p5e.notesapp.core.data.util.PreferencesManager
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Constants.KEY_JWT_TOKEN
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideAppSettingsDataStore(@ApplicationContext appContext: Context): DataStore<AppSettings>  {
        return DataStoreFactory.create(
            serializer = AppSettingsSerializer(CryptoManager()),
            produceFile = { appContext.dataStoreFile(Constants.DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }


    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .crossfade(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): PreferencesManager {
        return PreferencesManager(sharedPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(dataStore: DataStorePreferenceImpl): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                runBlocking {
                    val token = dataStore.getFirstPreference(USER_TOKEN, "")
                    val modifiedRequest = it.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    it.proceed(modifiedRequest)
                }
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
}