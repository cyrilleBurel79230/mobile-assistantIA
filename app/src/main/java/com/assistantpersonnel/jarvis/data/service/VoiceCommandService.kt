package com.assistantpersonnel.jarvis.data.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VoiceCommandService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

    fun startListening(onResult: (String) -> Unit) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR")
        }

        recognizer.setRecognitionListener(object : android.speech.RecognitionListener {


            override fun onResults(results: Bundle?) {
                val text = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()
                Log.d("JarvisVoice", "🧠 Texte reconnu : $text")

                text?.let { onResult(it) }
            }

            override fun onError(error: Int) {
                onResult("Erreur de reconnaissance vocale")
                Log.e("JarvisVoice", "❌ Erreur de reconnaissance : $error")

            }

            // Callbacks requis mais non utilisés ici
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d("JarvisVoice", "✅ Prêt à écouter")
            }

            override fun onBeginningOfSpeech() {
                Log.d("JarvisVoice", "🎙️ Début de la parole")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d("JarvisVoice", "🔊 Volume détecté : $rmsdB")
            }

            // Les autres callbacks peuvent aussi avoir des logs si besoin
            override fun onEndOfSpeech() {
                Log.d("JarvisVoice", "🔚 Fin de la parole")
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d("JarvisVoice", "📝 Résultat partiel : ${partialResults}")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d("JarvisVoice", "📦 Événement : $eventType")
            }

            override fun onBufferReceived(buffer: ByteArray?) {}

        })

        recognizer.startListening(intent)
    }

    fun stopListening() {
        recognizer?.stopListening()
        recognizer?.cancel()
    }

}