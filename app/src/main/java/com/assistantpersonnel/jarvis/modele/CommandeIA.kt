package com.assistantpersonnel.jarvis.modele

import java.time.LocalDateTime

data class CommandeIA(
    val texteCommande: String,
    val reponseJarvis: String,
    val date: LocalDateTime,
    val contexte: ContexteIA
)

enum class ContexteIA {
    CAVE,METEO,MAISON,PLANNING, JARDIN, SANTE,MAIL, GENERAL, INCONNU
}