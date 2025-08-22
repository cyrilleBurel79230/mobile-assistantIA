package com.assistantpersonnel.jarvis.ui.composants

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.assistantpersonnel.jarvis.modele.ContexteIA
import com.assistantpersonnel.jarvis.vueModele.HistoriqueIAViewModel

@Composable
fun BarreVocaleJarvis(
    historiqueVM: HistoriqueIAViewModel,
    commandeReconnue: String,
    onMicroClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("🎙️ Jarvis vous écoute", color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = commandeReconnue.ifEmpty { "Dites une commande..." },
                    color = Color.LightGray
                )
            }

            Button(onClick = {
                onMicroClicked()

                // Exemple de traitement de la commande
                val commande = "Ajoute une bouteille de Bordeaux 2018"
                val reponse = "Bouteille ajoutée à la cave"
                val contexte = ContexteIA.CAVE

                // Sauvegarde dans l'historique
                historiqueVM.ajouterCommande(commande, reponse, contexte)
            }) {
                Text("Parler")
            }

        }
    }
}