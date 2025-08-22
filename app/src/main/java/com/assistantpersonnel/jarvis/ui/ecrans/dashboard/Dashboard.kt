package com.assistantpersonnel.jarvis.ui.ecrans.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assistantpersonnel.jarvis.ui.composants.BarreVocaleJarvis
import com.assistantpersonnel.jarvis.ui.composants.CarteIA
import com.assistantpersonnel.jarvis.ui.composants.dashboard.CarteSection

@OptIn(ExperimentalMaterial3Api::class)

/**
 * Écran principal du tableau de bord Jarvis.
 * Affiche les différentes sections : cave, jardin, santé, etc.
 */
@Composable
fun EcranDashboard() {

    var commande by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barre vocale en haut
        BarreVocaleJarvis(
            commandeReconnue = commande,
            onMicroClicked = {
                // TODO : lancer reconnaissance vocale
                commande = "Ajoute une bouteille de Château Margaux 2015"
                // TODO : interpréter et exécuter la commande
            }
        )

        // Contenu principal du dashboard
        LazyColumn(modifier = Modifier.weight(1f)) {
/*
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            )*/
            item {
                CarteSection(
                    titre = "Ma Cave",
                    sousTitre = "24 articles",
                    description = "Dernier ajout : Château Margaux 2015",
                    boutonTexte = "Voir la cave",
                    onClick = { /* TODO: Navigation vers Cave */ }
                )
            }

            item {
                CarteSection(
                    titre = "Mon Jardin",
                    sousTitre = "3 plantes à arroser",
                    description = "Prochaine récolte : Tomates (7j)",
                    boutonTexte = "Planifier l’arrosage",
                    onClick = { /* TODO: Navigation vers Jardin */ }
                )
            }

            item {
                CarteSection(
                    titre = "Ma Santé",
                    sousTitre = "Glycémie : 112 mg/dL",
                    description = "Conseil du jour : Hydratation suffisante",
                    boutonTexte = "Enregistrer une mesure",
                    onClick = { /* TODO: Navigation vers Santé */ }
                )
            }
        }
        // Zone IA en bas (facultatif)
        CarteIA(
            message = "Conseil du jour : Pensez à vérifier votre cave.",
            onAction = { /* Action IA */ }
        )
    }
}