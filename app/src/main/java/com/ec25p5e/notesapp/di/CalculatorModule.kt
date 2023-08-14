package com.ec25p5e.notesapp.di

import com.ec25p5e.notesapp.feature_calc.domain.util.ExpressionWriter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalculatorModule {

    @Provides
    @Singleton
    fun provideExpressionWriter(): ExpressionWriter {
        return ExpressionWriter()
    }
}