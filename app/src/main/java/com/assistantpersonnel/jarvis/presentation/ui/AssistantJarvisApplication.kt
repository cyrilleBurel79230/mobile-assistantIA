package com.assistantpersonnel.jarvis.presentation.ui
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Point d’entrée de l’application Jarvis.
 * Initialise Hilt pour l’injection de dépendances.
 */
@HiltAndroidApp
class AssistantJarvisApplication : Application()