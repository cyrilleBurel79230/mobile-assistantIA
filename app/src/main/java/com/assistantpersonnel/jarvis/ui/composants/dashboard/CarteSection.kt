package com.assistantpersonnel.jarvis.ui.composants.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

/**
 * Composable réutilisable pour afficher une section du dashboard.
 * Utilisé pour la cave, le jardin, la santé, etc.
 */
@Composable
fun CarteSection(
    titre: String,
    sousTitre: String,
    description: String,
    boutonTexte: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(titre, style = MaterialTheme.typography.titleLarge, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(sousTitre, color = Color(0xFF60A5FA)) // bleu clair
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, color = Color.LightGray)
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onClick) {
                Text(boutonTexte)
            }
        }
    }
}