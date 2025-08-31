package com.assistantpersonnel.jarvis.presentation.ui.ecrans
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.assistantpersonnel.jarvis.presentation.ui.composants.BarreVocaleJarvis

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.assistantpersonnel.jarvis.presentation.ui.composants.CarteIA
import com.assistantpersonnel.jarvis.presentation.ui.composants.CarteNavigation
import com.assistantpersonnel.jarvis.presentation.viewmodel.HistoriqueIAViewModel
import com.assistantpersonnel.jarvis.presentation.viewmodel.JarvisViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime

@Composable
fun EcranJarvisAccueil(
   // historiqueVM: HistoriqueIAViewModel = viewModel(),
    jarvisVM: JarvisViewModel = viewModel(),
    navController: NavHostController
) {
    val isReady by jarvisVM.isJarvisReady.collectAsState()


    LaunchedEffect(isReady) {

        if (isReady && !jarvisVM.hasWelcomed  ) {
            val heure = LocalTime.now().hour
            val salutation = when {
                heure in 5..11 -> "Bon matin"
                heure in 12..17 -> "Bon après-midi"
                else -> "Bonsoir"
            }
            val message = "$salutation Cyrille. Jarvis est opérationnel et prêt à vous assister."

            jarvisVM.hasWelcomed = true

          /*  jarvisVM.ttsService.speak(message) {
                // ✅ Relancer l’écoute uniquement après la parole
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    jarvisVM.lancerReconnaissanceVocale(navController)
                }
            }*/
            jarvisVM.ttsService.speak(message) {
                // ✅ Utiliser une coroutine pour garantir le thread principal
                jarvisVM.viewModelScope.launch {
                    jarvisVM.lancerReconnaissanceVocale(navController)
                }
            }

        }

    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = if (jarvisVM.isJarvisActif) "🟢 Jarvis est actif" else "🟡 Jarvis est en veille",
            color = if (jarvisVM.isJarvisActif) Color.Green else Color(0xFFFFC107),
            modifier = Modifier.padding(8.dp)

        )
        Button(
            onClick = {
                jarvisVM.arreterJarvis()


            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)) // Bleu futuriste
        ) {
            Text("Arréter", color = Color.White)
        }

        // Zone IA en bas (facultatif)
        CarteIA(
            message = "Conseil du jour : Pensez à vérifier votre cave.",
            onAction = { /* Action IA */ }
        )
     //   jarvisVM.lancerReconnaissanceVocale("Je veux voir ma cave")
        // Cartes de navigation (cave, jardin, santé, etc.)
        /*
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                CarteNavigation("Ma Cave", "Gérez vos bouteilles", "Voir", onClick = {
                    navController.navigate("cave")
                })
            }
            item {
                CarteNavigation("Mon Jardin", "Suivi des plantes", "Voir", onClick = {
                    navController.navigate("jardin")
                })
            }
            item {
                CarteNavigation("Ma Santé", "Mesures et conseils", "Voir", onClick = {
                    navController.navigate("sante")
                })
            }
        }

        // Barre vocale Jarvis
        BarreVocaleJarvis(
            jarvisVM = jarvisVM,
            navController = navController,
            commandeReconnue = jarvisVM.commandeReconnue,
            reponseIA = jarvisVM.reponseIA,
            contexteIA = jarvisVM.contexteIA,
            onMicroClicked = {
                jarvisVM.lancerReconnaissanceVocale("Je veux voir ma cave")
            }
        )*/
    }
}