package com.assistantpersonnel.jarvis.domain.utils

import com.assistantpersonnel.jarvis.data.service.MistralService
import com.assistantpersonnel.jarvis.modele.ContexteIA
import javax.inject.Inject

class ContexteUtils  @Inject constructor(
      private val mistralService: MistralService
){

    fun extraireContexte(commande: String): ContexteIA {
        val texte = commande.lowercase()

        return when {
            // Cave à vin
            texte.contains("bouteille") || texte.contains("cave") || texte.contains("vin") -> ContexteIA.CAVE

            // Météo
            texte.contains("météo") || texte.contains("temps") || texte.contains("pluie") || texte.contains("soleil") -> ContexteIA.METEO

            // Maison / domotique
            texte.contains("lumière") || texte.contains("volet") || texte.contains("chauffage") || texte.contains("alarme") -> ContexteIA.MAISON

            // Agenda / organisation
            texte.contains("rendez-vous") || texte.contains("agenda") || texte.contains("calendrier") -> ContexteIA.PLANNING

            // Assistant général
            texte.contains("bonjour") || texte.contains("jarvis") || texte.contains("assistant") -> ContexteIA.GENERAL

            // Par défaut
            else -> ContexteIA.INCONNU
        }
    }
    suspend fun extraireContexteAvecIA(commande: String): ContexteIA {

        val prompt = "Analyse cette commande et donne-moi son domaine : \"$commande\". Réponds uniquement par un mot : CAVE, METEO, MAISON, PLANNING, GENERAL ou INCONNU."
        val reponse = mistralService.callIA(prompt).trim().uppercase()

        return when (reponse) {
            "CAVE" -> ContexteIA.CAVE
            "METEO" -> ContexteIA.METEO
            "JARDIN" -> ContexteIA.JARDIN
            "MAISON" -> ContexteIA.MAISON
            "SANTE" -> ContexteIA.SANTE
            "MAIL" -> ContexteIA.MAIL
            "PLANNING" -> ContexteIA.PLANNING
            "GENERAL" -> ContexteIA.GENERAL
            else -> ContexteIA.INCONNU
        }
    }
    private val motsCles = mapOf(
        ContexteIA.CAVE to listOf("bouteille", "cave", "vin"),
        ContexteIA.METEO to listOf("météo", "temps", "pluie", "soleil"),
        ContexteIA.MAISON to listOf("lumière", "volet", "chauffage", "alarme"),
        ContexteIA.PLANNING to listOf("rendez-vous", "agenda", "calendrier"),
        ContexteIA.GENERAL to listOf("bonjour", "jarvis", "assistant")
    )


}