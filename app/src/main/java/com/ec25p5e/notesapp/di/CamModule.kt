package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.feature_cam.data.remote.CamApi
import com.ec25p5e.notesapp.feature_cam.data.repository.CamRepositoryImpl
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository
import com.ec25p5e.notesapp.feature_cam.domain.use_case.CameraUseCases
import com.ec25p5e.notesapp.feature_cam.domain.use_case.GetCameraDetail
import com.ec25p5e.notesapp.feature_cam.domain.use_case.GetCameraForGlobalMap
import com.ec25p5e.notesapp.feature_cam.domain.use_case.GetCameraForRegions
import com.ec25p5e.notesapp.feature_cam.domain.use_case.GetCategories
import com.ec25p5e.notesapp.feature_cam.domain.use_case.GetContinents
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
object CamModule {


    @Provides
    @Singleton
    fun provideNoteApi(client: OkHttpClient): CamApi {
        return Retrofit.Builder()
            .baseUrl(CamApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCamRepository(
        api: CamApi
    ): CamRepository {
        return CamRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCamUseCases(repository: CamRepository): CameraUseCases {
        return CameraUseCases(
            getCameraForRegions = GetCameraForRegions(repository),
            getCameraDetail = GetCameraDetail(repository),
            getCameraForGlobalMap = GetCameraForGlobalMap(repository),
            getContinents = GetContinents(repository),
            getCategories = GetCategories(repository)
        )
    }
}