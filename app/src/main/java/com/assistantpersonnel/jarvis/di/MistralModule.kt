package com.assistantpersonnel.jarvis.di

import com.assistantpersonnel.jarvis.data.service.MistralService
import com.assistantpersonnel.jarvis.data.service.MistralServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MistralModule {

    @Provides
    @Singleton
    fun provideMistralService(): MistralService {
        return MistralServiceImpl()
    }
}