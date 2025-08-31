package com.assistantpersonnel.jarvis.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.assistantpersonnel.jarvis.data.service.MistralService
import com.assistantpersonnel.jarvis.data.service.TTSService
import com.assistantpersonnel.jarvis.data.service.VoiceCommandService
import com.assistantpersonnel.jarvis.domain.utils.ContexteUtils

import com.assistantpersonnel.jarvis.modele.ContexteIA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// JarvisViewModel.kt — logique de traitement des commandes vocales

@HiltViewModel
class JarvisViewModel @Inject constructor(
    private val voiceCommandService: VoiceCommandService,
    public val ttsService: TTSService,
    val mistralService: MistralService,
    val contexteUtils: ContexteUtils
) : ViewModel() {
    private var traitementJob: Job? = null

    val isJarvisReady: StateFlow<Boolean> get() = ttsService.isJarvisReady

    var isJarvisActif by mutableStateOf(true)
    var isProcessing by mutableStateOf(false)

    var hasWelcomed by mutableStateOf(false)

    var commandeReconnue by
    mutableStateOf("")
    var reponseIA by mutableStateOf("")
    var contexteIA by mutableStateOf(ContexteIA.INCONNU)
    // Fonction appelée quand Jarvis reçoit une commande vocale

   // fun speak(text: String) {
   //     ttsService.speak(text)
   // }

    private var isListening = false

    fun lancerReconnaissanceVocale(navController: NavHostController? = null) {
        if (isListening) return
        isListening = true
        voiceCommandService.startListening(
            onResult = { texte ->
                isListening = false
                traiterCommande(texte, navController)
                       },
            canListen = { !isProcessing }
        )
    }


    private fun traiterCommande(texte: String, navController: NavHostController?) {
        if (texte.isBlank() || isProcessing) return

        if (!isJarvisActif) {
            isJarvisActif = true
            ttsService.speak("Je suis de retour Cyrille. Je vous écoute.") {
                lancerReconnaissanceVocale(navController)
            }
            return
        }

        if (texte.length < 4 || texte == commandeReconnue) {
            lancerReconnaissanceVocale(navController)
            return
        }


        isProcessing = true
        commandeReconnue = texte

        traitementJob = viewModelScope.launch {
            try {
                val reponse = mistralService.callIA(texte)
                if (!isJarvisActif) return@launch

                reponseIA = reponse
                contexteIA = contexteUtils.extraireContexteAvecIA(texte)

                ttsService.speak(reponse) {
                    isProcessing = false
                    lancerReconnaissanceVocale(navController)
                }

                navController?.let {
                    when (contexteIA) {
                        ContexteIA.CAVE -> it.navigate("cave")
                        ContexteIA.JARDIN -> it.navigate("jardin")
                        ContexteIA.SANTE -> it.navigate("sante")
                        else -> {}
                    }
                }

            } catch (e: Exception) {
                reponseIA = "Je n'ai pas pu traiter la commande."
                ttsService.speak(reponseIA) {
                    isProcessing = false
                    lancerReconnaissanceVocale(navController)
                }
            }
        }
    }

    fun traiterCommandeEtNaviguer(
        commande: String,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            val contexte = contexteUtils.extraireContexteAvecIA(commande)
            when (contexte) {
                ContexteIA.CAVE -> navController.navigate("cave")
                ContexteIA.JARDIN -> navController.navigate("jardin")
                ContexteIA.SANTE -> navController.navigate("sante")
                ContexteIA.METEO -> navController.navigate("meteo")
                ContexteIA.MAIL -> navController.navigate("mail")
                ContexteIA.PLANNING -> navController.navigate("planning")
                else -> {
                    reponseIA = "Je n’ai pas compris le domaine. Veux-tu parler de ta cave, ton jardin ou ta santé ?"
                    ttsService.speak(reponseIA)
                }
            }
        }
    }
    fun arreterJarvis() {
        isJarvisActif = false
        traitementJob?.cancel()
        ttsService.stopSpeaking()
        // Jarvis reste à l’écoute
        ttsService.speak("Très bien Cyrille, je me mets en veille. Je reste à l’écoute.") {
            viewModelScope.launch {
                lancerReconnaissanceVocale()
            }
        }

        Log.d("JarvisVM", "🛌 Jarvis en sommeil léger")


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