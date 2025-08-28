package com.assistantpersonnel.jarvis.presentation.ui
import android.Manifest

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import com.assistantpersonnel.jarvis.presentation.ui.ecrans.dashboard.EcranDashboard
import com.assistantpersonnel.jarvis.presentation.ui.theme.AssistantJarvisTheme
import com.assistantpersonnel.jarvis.presentation.viewmodel.JarvisViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


/**
 * Activité principale de l'application.
 * Initialise Jetpack Compose, la reconnaissance vocale et la synthèse vocale.
 * Affiche le Dashboard principal de Jarvis.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Instance de TextToSpeech pour faire parler Jarvis
    private lateinit var tts: TextToSpeech

    // Instance de SpeechRecognizer pour écouter les commandes vocales
    private lateinit var speechRecognizer: SpeechRecognizer

    // ViewModel injecté via Hilt pour gérer la logique de Jarvis
    private val jarvisViewModel: JarvisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            100
        )

        // Initialisation de la synthèse vocale
        initTextToSpeech()

        // Initialisation de la reconnaissance vocale
        initSpeechRecognizer()

        // Lancement de l'écoute vocale dès le démarrage
        startListening()

        // Définition du contenu de l'activité avec Jetpack Compose
        setContent {
            // Application du thème personnalisé Jarvis
            AssistantJarvisTheme {
                // Surface = fond de l'écran avec la couleur du thème
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Affichage de l'écran principal : le Dashboard
                    EcranDashboard()
                }
            }
        }
    }

    /**
     * Initialise le moteur de synthèse vocale avec une voix grave et posée.
     */
    private fun initTextToSpeech() {
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.FRANCE // Langue française
                tts.setPitch(0.8f)           // Voix plus grave
                tts.setSpeechRate(0.95f)     // Débit plus posé
                tts.speak("Bonjour Cyrille. Jarvis est opérationnel.",
                    TextToSpeech.QUEUE_FLUSH, null, "JARVIS_INIT")
            }
        }
    }

    /**
     * Initialise le moteur de reconnaissance vocale.
     */
    private fun initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRANCE)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Parlez, Cyrille. Jarvis vous écoute.")
        }

        // Définition du listener pour capter les résultats vocaux
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val command = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                command?.let {
                    //jarvisViewModel.handleVoiceCommand(it) // Envoie la commande au ViewModel
                    jarvisViewModel.lancerReconnaissanceVocale(it) // Envoie la commande au ViewModel
                }
            }

            // Callbacks requis mais non utilisés ici
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    /**
     * Démarre l'écoute vocale.
     */
    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRANCE)
        }
        Log.d("MainActivity", "startListening")

        speechRecognizer.startListening(intent)
    }

    /**
     * Nettoyage des ressources vocales à la fermeture de l'activité.
     */
    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
        speechRecognizer.destroy()
    }
}