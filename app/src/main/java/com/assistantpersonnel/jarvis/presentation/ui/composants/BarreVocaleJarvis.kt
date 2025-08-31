package com.assistantpersonnel.jarvis.presentation.ui.composants

import android.R.attr.alpha
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavHostController
import com.assistantpersonnel.jarvis.modele.ContexteIA
//import com.assistantpersonnel.jarvis.presentation.viewmodel.HistoriqueIAViewModel
import com.assistantpersonnel.jarvis.presentation.viewmodel.JarvisViewModel



@Composable
fun EcranJarvisAccueil(
   // historiqueVM: HistoriqueIAViewModel,
    jarvisVM: JarvisViewModel,
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // ... tes cartes de navigation

        BarreVocaleJarvis(
         //   historiqueVM = historiqueVM,
            jarvisVM = jarvisVM,
            navController = navController,
            commandeReconnue = jarvisVM.commandeReconnue,
            reponseIA = jarvisVM.reponseIA,
            contexteIA = jarvisVM.contexteIA,
            onMicroClicked = {
            //    jarvisVM.lancerReconnaissanceVocale(navController)
            }
        )
    }
}


@Composable
fun BarreVocaleJarvis(
  //  historiqueVM: HistoriqueIAViewModel, // ViewModel qui gère l’historique des commandes
    jarvisVM: JarvisViewModel,
    commandeReconnue: String,            // Texte reconnu par la voix
    reponseIA: String,
    contexteIA: ContexteIA,
    navController: NavHostController,
    onMicroClicked: () -> Unit           // Callback déclenché quand on clique sur le micro
) {
    // Animation de pulsation si Jarvis est en écoute
    val isListening = commandeReconnue.isNotEmpty()
    val alphaAnim by animateFloatAsState(
        targetValue = if (isListening) 1f else 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "AlphaAnimation"
    )


    // Carte stylisée qui contient la barre vocale
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .graphicsLayer { alpha = alphaAnim }, // effet de pulsation
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)) // Couleur sombre façon Jarvis
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Ligne horizontale avec texte à gauche et bouton à droite
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
            // Colonne contenant le titre et la commande reconnue
                Column(modifier = Modifier.weight(1f)) {
                    Text("🎙️ Jarvis vous écoute", color = Color.White, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = commandeReconnue.ifEmpty { "Dites une commande..." },
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            // Bouton pour déclencher l’écoute vocale
                Button(
                    onClick = {
                        onMicroClicked() // Déclenche l’écoute vocale

                        // Exemple de commande simulée (à remplacer par la vraie reconnaissance vocale)
                        // val commande = "Ajoute une bouteille de Bordeaux 2018"
                        // val reponse = "Bouteille ajoutée à la cave"
                        // val contexte = ContexteIA.CAVE

                        // Enregistrement dans l’historique via le ViewModel
                        //historiqueVM.ajouterCommande(commande, reponse, contexte)

                        //val commande = commandeReconnue
                        //val reponse = traiterCommande(commande) // fonction à créer
                        //val contexte = extraireContexte(commande) // fonction à créer
                        //historiqueVM.ajouterCommande(commande, reponse, contexte)
                        jarvisVM.traiterCommandeEtNaviguer(commandeReconnue, navController)

                        jarvisVM.arreterJarvis()

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)) // Bleu futuriste
                ) {
                    Text("Arréter", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Zone de réponse IA
            if (reponseIA.isNotEmpty()) {
                /*
                ReponseJarvis(
                    reponse = reponseIA,
                    contexte = contexteIA,
                    modifier = Modifier.fillMaxWidth()
                )*/
                Text("🧠 ${reponseIA}", color = Color(0xFF38BDF8))
                Text("Contexte : ${contexteIA.name}", color = Color.Gray)

            }
        }
    }
}