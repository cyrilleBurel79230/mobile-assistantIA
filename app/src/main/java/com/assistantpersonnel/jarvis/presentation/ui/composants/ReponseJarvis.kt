package com.assistantpersonnel.jarvis.presentation.ui.composants

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assistantpersonnel.jarvis.modele.ContexteIA
import kotlinx.coroutines.delay

@Composable
fun ReponseJarvis(
    reponse: String,
    contexte: ContexteIA,
    modifier: Modifier = Modifier
) {
    var texteAffiche by remember { mutableStateOf("") }

    // Animation d’écriture progressive
    LaunchedEffect(reponse) {
        texteAffiche = ""
        reponse.forEachIndexed { index, char ->
            delay(30) // vitesse d’écriture
            texteAffiche += char
        }
    }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = "🧠 Réponse de Jarvis :",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = texteAffiche,
            color = Color(0xFF38BDF8),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Contexte : ${contexte.name}",
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
    }
}