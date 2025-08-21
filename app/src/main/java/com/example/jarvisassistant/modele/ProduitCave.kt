package com.example.jarvisassistant.modele

import java.time.LocalDate

// Classe scellée qui représente les différents types d'objets dans la cave
sealed class ProduitCave {
    // Représente une bouteille de vin
    data class Vin(
        val nom: String,
        val annee: Int,
        val region: String,
        val quantite: Int
    ) : ProduitCave()

    // Représente une bière
    data class Biere(
        val nom: String,
        val style: String,
        val brasserie: String,
        val quantite: Int
    ) : ProduitCave()

    // Représente un fromage en cours d'affinage
    data class Fromage(
        val nom: String,
        val type: String,
        val dateAffinage: LocalDate
    ) : ProduitCave()
}