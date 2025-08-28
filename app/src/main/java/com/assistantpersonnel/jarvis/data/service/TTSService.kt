package com.assistantpersonnel.jarvis.data.service

import android.content.Context
import android.speech.tts.TextToSpeech
import com.assistantpersonnel.jarvis.domain.model.VoiceStyle
import dagger.hilt.android.qualifiers.ApplicationContext
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

    init {
        // Initialisation du moteur TTS avec configuration vocale personnalisée
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
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
            }
        }
    }

    /**
     * Fait parler Jarvis avec le texte fourni.
     * @param text Le message à prononcer
     */
    fun speak(text: String, style: VoiceStyle = VoiceStyle.NEUTRE) {
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
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "JARVIS_SPEAK")
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
