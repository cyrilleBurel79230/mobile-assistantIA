package com.assistantpersonnel.jarvis.reseau

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

// Service pour envoyer les données vers Google Sheets via HTTP POST
object SheetsService {
    fun envoyer(url: String, payload: SheetsPayload, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val json = JSONObject().apply {
            put("ligne", JSONArray(payload.ligne))
        }

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                onSuccess()
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }
        })
    }

}
