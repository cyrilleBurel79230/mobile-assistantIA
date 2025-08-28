package com.assistantpersonnel.jarvis.data.service

interface MistralService {

    suspend fun callIA(prompt: String): String

}