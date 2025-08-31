package com.assistantpersonnel.jarvis.presentation.ui.ecrans.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.assistantpersonnel.jarvis.presentation.ui.composants.BarreVocaleJarvis
import com.assistantpersonnel.jarvis.presentation.ui.composants.CarteIA
import com.assistantpersonnel.jarvis.presentation.ui.composants.dashboard.CarteSection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.assistantpersonnel.jarvis.presentation.viewmodel.HistoriqueIAViewModel
import com.assistantpersonnel.jarvis.presentation.viewmodel.JarvisViewModel

@OptIn(ExperimentalMaterial3Api::class)

/**
 * Écran principal du tableau de bord Jarvis.
 * Affiche les différentes sections : cave, jardin, santé, etc.
 */
@Composable
fun EcranDashboard() {

    var commande by remember { mutableStateOf("") }
    val historiqueVM: HistoriqueIAViewModel = viewModel()
    val jarvisVM: JarvisViewModel = viewModel()


    Column(modifier = Modifier.fillMaxSize()) {
        // Barre vocale en haut
        /*
        BarreVocaleJarvis(
            historiqueVM = historiqueVM,
            jarvisVM = jarvisVM,
            commandeReconnue = commande,
            reponseIA = historiqueVM.reponseIA,
            contexteIA = historiqueVM.contexteIA,

            onMicroClicked = {
                val texte = "Ajoute une bouteille de Château Margaux 2015"
               // historiqueVM.traiterCommande(texte)
            }

        )*/

        // Contenu principal du dashboard
        LazyColumn(modifier = Modifier.weight(1f)) {
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