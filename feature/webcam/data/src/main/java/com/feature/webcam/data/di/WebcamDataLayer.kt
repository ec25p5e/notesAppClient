package com.feature.webcam.data.di

import com.ec25p5e.notesapp.feature_cam.data.remote.CamApi
import com.ec25p5e.notesapp.feature_cam.data.repository.CamRepositoryImpl
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository
import com.feature.webcam.data.remote.WebcamApi
import com.feature.webcam.data.repository.WebcamRepositoryImpl
import com.feature.webcam.domain.repository.WebcamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WebcamDataLayer {

    @Provides
    @Singleton
    fun provideWebcamApi(): WebcamApi {
        return Retrofit.Builder()
            .baseUrl(WebcamApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebcamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCamRepository(api: WebcamApi): WebcamRepository {
        return WebcamRepositoryImpl(api)
    }
}