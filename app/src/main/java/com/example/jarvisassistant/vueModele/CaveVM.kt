package com.example.jarvisassistant.vueModele

import com.example.jarvisassistant.modele.ProduitCave
import java.time.LocalDate

// ViewModel fictif qui fournit une liste d'objets de cave
class CaveVM {
    // Liste statique pour l'exemple
    val produits = listOf(
        ProduitCave.Vin("Château Margaux", 2015, "Bordeaux", 3),
        ProduitCave.Biere("IPA Artisanale", "IPA", "Brasserie du Coin", 6),
        ProduitCave.Fromage("Comté", "Affiné 24 mois", LocalDate.of(2025, 9, 1))
    )
}