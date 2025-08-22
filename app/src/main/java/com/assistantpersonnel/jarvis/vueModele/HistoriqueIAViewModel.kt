package com.assistantpersonnel.jarvis.vueModele

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.assistantpersonnel.jarvis.modele.CommandeIA
import com.assistantpersonnel.jarvis.modele.ContexteIA
import java.time.LocalDateTime

class HistoriqueIAViewModel : ViewModel() {
    private val _historique = mutableStateListOf<CommandeIA>()
    val historique: List<CommandeIA> = _historique

    fun ajouterCommande(commande: String, reponse: String, contexte: ContexteIA) {
        _historique.add(
            CommandeIA(
                texteCommande = commande,
                reponseJarvis = reponse,
                date = LocalDateTime.now(),
                contexte = contexte
            )
        )
    }
}