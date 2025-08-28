package com.assistantpersonnel.jarvis.reseau


/**
* Structure de données envoyée à Google Sheets.
* Chaque ligne représente une entrée dans une feuille spécifique.
*/

data class SheetsPayload(
   val ligne: List<String>
)