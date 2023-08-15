package com.ec25p5e.notesapp.di

import android.content.Context
import com.ec25p5e.notesapp.feature_bluetooth.data.local.BluetoothControllerImpl
import com.ec25p5e.notesapp.feature_bluetooth.domain.repository.BluetoothController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController {
        return BluetoothControllerImpl(context)
    }
}