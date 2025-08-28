package com.assistantpersonnel.jarvis.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.assistantpersonnel.jarvis.data.service.MistralService
import com.assistantpersonnel.jarvis.data.service.TTSService
import com.assistantpersonnel.jarvis.data.service.VoiceCommandService
import com.assistantpersonnel.jarvis.domain.utils.ContexteUtils
import com.assistantpersonnel.jarvis.domain.utils.ContexteUtils.*
import com.assistantpersonnel.jarvis.modele.CommandeIA
import com.assistantpersonnel.jarvis.modele.ContexteIA
import com.assistantpersonnel.jarvis.reseau.SheetsEndpoints
import com.assistantpersonnel.jarvis.reseau.SheetsPayload
import com.assistantpersonnel.jarvis.reseau.SheetsService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// ViewModel qui gère l'historique des commandes IA

@HiltViewModel
class HistoriqueIAViewModel @Inject constructor(
    private val voiceCommandService: VoiceCommandService,
    private val ttsService: TTSService,
    private val mistralService: MistralService,
    private val contexteUtils: ContexteUtils

) : ViewModel() {

    var commandeReconnue by mutableStateOf("")
    var reponseIA by mutableStateOf("")
    var contexteIA by mutableStateOf(ContexteIA.INCONNU)

    fun lancerReconnaissanceVocale() {
       /* voiceCommandService.startListening { texte ->
            commandeReconnue = texte

            // Appel à Mistral pour générer la réponse
            val reponse = mistralService.callIA(texte)
            reponseIA = reponse

            // Déduction du contexte via IA
            contexteIA = contexteUtils.extraireContexteAvecIA(texte)

            // Jarvis parle la réponse
            ttsService.speak(reponse)

            // Historique si tu veux le conserver
            ajouterCommande(texte, reponse, contexteIA)*/
        }
    }


    fun ajouterCommande(commande: String, reponse: String, contexte: ContexteIA) {
        // Ajoute à l’historique (à implémenter selon ta logique)
    }
/*
    fun traiterCommande(texte: String) {
        commandeReconnue = texte
        reponseIA = mistralService.callIA(texte)
        contexteIA = contexteUtils.extraireContexteAvecIA(texte)
        ttsService.speak(reponseIA)
        ajouterCommande(texte, reponseIA, contexteIA)
    }
*/







/*

class HistoriqueIAViewModel : ViewModel() {
    private val _historique = mutableListOf<CommandeIA>()
    val historique: List<CommandeIA> get() = _historique

    fun ajouterCommande(texte: String, reponse: String, contexte: ContexteIA) {
        val commande = CommandeIA(
            texteCommande = texte,
            reponseJarvis = reponse,
            date = LocalDateTime.now(),
            contexte = contexte
        )
        _historique.add(commande)
        // Préparation du payload pour Google Sheets
        val payload = SheetsPayload(
            ligne = listOf(
                commande.texteCommande,
                commande.reponseJarvis,
                commande.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                commande.contexte.name
            )
        )

        // Envoi vers Google Sheets
        SheetsService.envoyer(
            url = SheetsEndpoints.URL_BASE_DONNE_IA,
            payload = payload,
            onSuccess = { /* Optionnel : notifier l'UI */ },
            onError = { e -> /* Optionnel : log ou toast */ }
        )

    }
}
*/
