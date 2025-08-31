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
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.assistantpersonnel.jarvis.presentation.ui.ecrans.EcranJarvisAccueil
import com.assistantpersonnel.jarvis.presentation.ui.ecrans.cave.EcranCave

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


        // Définition du contenu de l'activité avec Jetpack Compose
        setContent {
            // Application du thème personnalisé Jarvis
            AssistantJarvisTheme {
                // Surface = fond de l'écran avec la couleur du thème
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Affichage de l'écran principal : le Dashboard
                    //EcranDashboard()
                    JarvisApp(jarvisViewModel)

                }
            }
        }
    }
}
@Composable
fun JarvisApp(jarvisVM: JarvisViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Accueil") {
        composable("Accueil") {
            EcranJarvisAccueil(jarvisVM = jarvisVM, navController = navController)
        }
        composable("cave") { EcranCave() }
      //  composable("jardin") { EcranJardin() }
      //  composable("sante") { EcranSante() }
        // Ajoute d'autres écrans ici
    }
}
