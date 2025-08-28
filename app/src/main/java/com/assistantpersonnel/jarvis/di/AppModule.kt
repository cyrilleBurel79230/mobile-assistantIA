package com.assistantpersonnel.jarvis.di

import android.content.Context
import com.assistantpersonnel.jarvis.data.service.TTSService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// AppModule.kt — configuration Hilt pour injecter les services

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTTSService(@ApplicationContext context: Context): TTSService {
        return TTSService(context)
    }
}