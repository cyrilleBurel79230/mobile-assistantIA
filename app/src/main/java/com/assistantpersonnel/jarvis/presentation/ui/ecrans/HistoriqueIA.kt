package com.assistantpersonnel.jarvis.presentation.ui.ecrans

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assistantpersonnel.jarvis.presentation.viewmodel.HistoriqueIAViewModel
import java.time.format.DateTimeFormatter

@Composable
fun HistoriqueIA(viewModel: HistoriqueIAViewModel) {
    /*
    Column(modifier = Modifier.padding(16.dp)) {
        Text("🧠 Historique des commandes IA", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        viewModel.historique.forEach { commande ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Commande : ${commande.texteCommande}", color = Color.White)
                    Text("Réponse : ${commande.reponseJarvis}", color = Color.LightGray)
                    Text("Contexte : ${commande.contexte}", color = Color(0xFF60A5FA))
                    Text("Date : ${commande.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}", color = Color.Gray)
                }
            }
        }
    }*/
}