package com.feature.webcam.domain.di

import com.feature.webcam.domain.repository.WebcamRepository
import com.feature.webcam.domain.use_cases.GetWebcamsForRegions
import com.feature.webcam.domain.use_cases.WebcamUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WebcamDomainLayer {

    @Provides
    @Singleton
    fun provideWebcamUseCases(repository: WebcamRepository): WebcamUseCases {
        return WebcamUseCases(
            getWebcamsForRegions = GetWebcamsForRegions(repository)
        )
    }
}