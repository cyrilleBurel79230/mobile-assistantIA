package com.assistantpersonnel.jarvis.di

import android.content.Context
import com.assistantpersonnel.jarvis.data.service.VoiceCommandService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceModule {

    @Provides
    @Singleton
    fun provideVoiceCommandService(@ApplicationContext context: Context): VoiceCommandService {
        return VoiceCommandService(context)
    }
}