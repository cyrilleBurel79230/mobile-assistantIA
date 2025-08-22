package com.assistantpersonnel.jarvis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.assistantpersonnel.jarvis.ui.ecrans.dashboard.EcranDashboard
import com.assistantpersonnel.jarvis.ui.theme.AssistantJarvisTheme

/**
 * Activité principale de l'application.
 * Initialise Jetpack Compose et affiche le Dashboard.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Définition du contenu de l'activité avec Jetpack Compose
        setContent {
            // Application du thème personnalisé
            AssistantJarvisTheme {
                // Surface = fond de l'écran avec la couleur du thème
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Affichage de l'écran principal : le Dashboard
                    EcranDashboard()
                }

            }
        }
    }
}