package com.assistantpersonnel.jarvis.data.service

import com.assistantpersonnel.jarvis.domain.model.ChatRequest
import com.assistantpersonnel.jarvis.domain.model.Message
import com.assistantpersonnel.jarvis.domain.model.MistralResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class MistralServiceImpl : MistralService {
// Le bloc withContext(Dispatchers.IO) garantit que l’appel réseau se fait sur un thread dédié
    override suspend fun callIA(prompt: String): String = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val gson = Gson()

        val systemPrompt = "Tu es une IA experte en vin, spécialisée dans l’analyse de textes OCR."
        val messages = listOf(
            Message("system", systemPrompt),
            Message("user", prompt)
        )

        val chatRequest = ChatRequest(
            model = "mistral-small-2506",
            messages = messages,
            temperature = 0.7,
            top_p = 0.95,
            max_tokens = 4000
        )

        val jsonBody = gson.toJson(chatRequest)

        val request = Request.Builder()
            .url("https://api.mistral.ai/v1/chat/completions")
            .addHeader("Authorization", "Bearer dUtZj5glXsrjGweM17peBOUTYYir7Kf9")
            .addHeader("Content-Type", "application/json")
            .post(jsonBody.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: return@withContext ""
            val json = gson.fromJson(body, MistralResponse::class.java)
            return@withContext json.choices.firstOrNull()?.message?.content ?: ""
        }
    }
}