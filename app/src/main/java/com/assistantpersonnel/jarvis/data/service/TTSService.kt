package com.assistantpersonnel.jarvis.data.service

import android.content.Context
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import com.assistantpersonnel.jarvis.domain.model.VoiceStyle
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

import javax.inject.Inject

// TTSService.kt — service dédié à la synthèse vocale (Text-to-Speech)

/**
 * Service dédié à la synthèse vocale (Text-to-Speech) pour Jarvis.
 * Injecté via Hilt et initialisé dès la création.
 */

class TTSService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var tts: TextToSpeech? = null
    private val _isJarvisReady = MutableStateFlow(false)
    val isJarvisReady: StateFlow<Boolean> get() = _isJarvisReady


    init {
        // Initialisation du moteur TTS avec configuration vocale personnalisée
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d("JarvisTTS", "✅ TTS initialisé avec succès")

                // Langue française
                tts?.language = Locale.FRANCE

                // Voix grave et posée façon Jarvis
                tts?.setPitch(0.8f)
                tts?.setSpeechRate(0.95f)


                // Optionnel : choisir une voix masculine si disponible
                val jarvisVoice = tts?.voices?.find {
                    it.locale.language == "fr" && it.name.contains("male", ignoreCase = true)
                }
                jarvisVoice?.let { tts?.voice = it }
                // Petit délai pour laisser le moteur se stabiliser
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    _isJarvisReady.value = true
                }, 500)

            }else{
                Log.e("JarvisTTS", "❌ Échec initialisation TTS : $status")

            }
        }
    }

    /**
     * Fait parler Jarvis avec le texte fourni.
     * @param text Le message à prononcer
     */
    fun speak(
        text: String,
        style: VoiceStyle = VoiceStyle.NEUTRE,
        onDone: (() -> Unit)? = null
    ) {
        when (style) {
            VoiceStyle.FORMEL -> {
                tts?.setPitch(1.0f)
                tts?.setSpeechRate(0.9f)
            }
            VoiceStyle.COMPLICE -> {
                tts?.setPitch(0.85f)
                tts?.setSpeechRate(1.1f)
            }
            VoiceStyle.SARCASTIQUE -> {
                tts?.setPitch(1.2f)
                tts?.setSpeechRate(0.8f)
            }
            VoiceStyle.NEUTRE -> {
                tts?.setPitch(1.0f)
                tts?.setSpeechRate(1.0f)
            }
        }

        if (!isJarvisReady.value) return

        tts?.setOnUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    Log.d("JarvisTTS", "🗣️ Début de la parole")
                }

                override fun onDone(utteranceId: String?) {
                    Log.d("JarvisTTS", "✅ Fin de la parole")
                    onDone?.invoke()
                }

                override fun onError(utteranceId: String?) {
                    Log.e("JarvisTTS", "❌ Erreur TTS sur $utteranceId")
                    onDone?.invoke()
                }
            })

            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "JARVIS_SPEAK")
            Log.d("JarvisTTS", "🔊 Jarvis parle : $text")

    }
  /**
     * Nettoie les ressources TTS. À appeler dans onDestroy().
     */
    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
    fun stopSpeaking() {
        tts?.stop()
    }

}
