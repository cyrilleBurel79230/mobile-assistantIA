package com.assistantpersonnel.jarvis.ui.ecrans.cave

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assistantpersonnel.jarvis.ui.composants.cave.CarteProduit
import com.assistantpersonnel.jarvis.vueModele.CaveVM

class CaveScreen {

}

@Composable
fun EcranCave(viewModel: CaveVM = CaveVM()) {
    // Colonne verticale qui contient tous les éléments
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ma Cave Personnelle", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Parcours de la liste des produits
        viewModel.produits.forEach { produit ->
            CarteProduit(produit)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}