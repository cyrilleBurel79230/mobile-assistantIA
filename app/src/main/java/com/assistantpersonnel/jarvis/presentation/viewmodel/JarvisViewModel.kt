package com.assistantpersonnel.jarvis.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assistantpersonnel.jarvis.data.service.MistralService
import com.assistantpersonnel.jarvis.data.service.TTSService
import com.assistantpersonnel.jarvis.data.service.VoiceCommandService
import com.assistantpersonnel.jarvis.domain.utils.ContexteUtils

import com.assistantpersonnel.jarvis.modele.ContexteIA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// JarvisViewModel.kt — logique de traitement des commandes vocales

@HiltViewModel
class JarvisViewModel @Inject constructor(
    private val voiceCommandService: VoiceCommandService,
    private val ttsService: TTSService,
    private val mistralService: MistralService,
    private val contexteUtils: ContexteUtils
) : ViewModel() {
    private var traitementJob: Job? = null

    var commandeReconnue by
    mutableStateOf("")
    var reponseIA by mutableStateOf("")
    var contexteIA by mutableStateOf(ContexteIA.INCONNU)
    // Fonction appelée quand Jarvis reçoit une commande vocale
    fun lancerReconnaissanceVocale(textInput: String) {
        // Appel à ton service vocal
        // Supposons que voiceService.startListening() appelle ce callback :
        Log.d("JarvisVM", "🎙️ Lancement de l’écoute vocale")

        voiceCommandService.startListening { texte ->
            commandeReconnue = texte
            Log.d("JarvisVM", "🗣️ Commande reçue : $texte")

            traitementJob =  viewModelScope.launch {
                try {
                    val reponse = mistralService.callIA(texte) // suspend function
                    reponseIA = reponse
                    Log.d("JarvisVM", "🧠 Réponse IA : $reponseIA")

                    contexteIA = contexteUtils.extraireContexteAvecIA(texte)
                    Log.d("JarvisVM", "📍 Contexte détecté : $contexteIA")

                    ttsService.speak(reponse)
                    Log.d("JarvisVM", "🔊 Réponse prononcée")

                    ajouterCommande(texte, reponse, contexteIA)
                } catch (e: Exception) {
                    Log.e("JarvisVM", "❌ Erreur IA : ${e.message}")
                    reponseIA = "Je n'ai pas pu traiter la commande."
                    ttsService.speak(reponseIA)
                }
            }
        }

    }
    fun arreterJarvis() {
        traitementJob?.cancel()
        voiceCommandService.stopListening()
        ttsService.stopSpeaking()
        Log.d("JarvisVM", "🛑 Jarvis arrêté")
    }

    fun ajouterCommande(commande: String, reponse: String, contexte: ContexteIA) {
        // Ajout dans une liste ou base locale
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.shutdown()
    }







    fun handleVoiceCommand(command: String) {
        when {
            command.contains("météo", ignoreCase = true) -> {
                ttsService.speak("La météo est ensoleillée à Vouillé, Cyrille.")
            }
            command.contains("heure", ignoreCase = true) -> {
                val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                ttsService.speak("Il est actuellement $currentTime.")
            }
            else -> {
                ttsService.speak("Je n'ai pas compris la commande, Cyrille.")
            }
        }
    }
}