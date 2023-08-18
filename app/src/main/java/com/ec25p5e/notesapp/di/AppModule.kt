package com.ec25p5e.notesapp.di

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import coil.ImageLoader
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver
import com.ec25p5e.notesapp.core.data.local.connectivity.NetworkConnectivityObserver
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants.USER_TOKEN
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.core.data.local.encryption.CryptoManager
import com.ec25p5e.notesapp.core.data.local.scheduler.AndroidAlarmScheduler
import com.ec25p5e.notesapp.core.data.local.serializer.AppSettingsSerializer
import com.ec25p5e.notesapp.core.domain.repository.AlarmScheduler
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_profile.domain.use_case.GetOwnUserIdCase
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Singleton
    @Provides
    fun provideAlarmScheduler(@ApplicationContext appContext: Context): AndroidAlarmScheduler {
        return AndroidAlarmScheduler(appContext)
    }

    @Singleton
    @Provides
    fun provideCryptoManager(): CryptoManager {
        return CryptoManager()
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext appContext: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(appContext)
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
    fun provideAes(): AESEncryptor {
        return AESEncryptor
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
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
                        .addHeader("x-windy-api-key", "DGj7oNfEUA8UMU9hCuGZQbqi9tQyJjcZ")
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

    @Provides
    @Singleton
    fun provideGetOwnUserIdUserCase(preferenceImpl: DataStorePreferenceImpl): GetOwnUserIdCase {
        return GetOwnUserIdCase(preferenceImpl)
    }
}