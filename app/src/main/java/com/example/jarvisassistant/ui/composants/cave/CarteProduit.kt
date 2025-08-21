package com.example.jarvisassistant.ui.composants.cave

import androidx.compose.runtime.Composable

class CarteProduit {
}
  @Composable
    fun CarteProduit(produit: ProduitCave) {
        // Carte avec coins arrondis et ombre
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            // Contenu de la carte
            Column(modifier = Modifier.padding(16.dp)) {
                // Affichage conditionnel selon le type d'objet
                when (produit) {
                    is ProduitCave.Vin -> {
                        Text("Vin : ${produit.nom}", style = MaterialTheme.typography.titleMedium)
                        Text("Année : ${produit.annee}")
                        Text("Région : ${produit.region}")
                        Text("Quantité : ${produit.quantite} bouteilles")
                    }
                    is ProduitCave.Biere -> {
                        Text("Bière : ${produit.nom}", style = MaterialTheme.typography.titleMedium)
                        Text("Style : ${produit.style}")
                        Text("Brasserie : ${produit.brasserie}")
                        Text("Quantité : ${produit.quantite} bouteilles")
                    }
                    is ProduitCave.Fromage -> {
                        Text("Fromage : ${produit.nom}", style = MaterialTheme.typography.titleMedium)
                        Text("Type : ${produit.type}")
                        Text("Affinage depuis : ${produit.dateAffinage}")
                    }
                }
            }
        }
    }


