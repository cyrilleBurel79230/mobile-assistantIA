package com.assistantpersonnel.jarvis.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Définition des couleurs personnalisées
private val CouleursFoncees = darkColorScheme(
    primary = Color(0xFF3B82F6),        // Bleu néon
    onPrimary = Color.White,
    background = Color(0xFF0F172A),     // Fond sombre
    onBackground = Color.White,
    surface = Color(0xFF1E293B),        // Cartes
    onSurface = Color.White
)

/**
 * Thème principal de l'application Assistant Jarvis.
 * Appliqué à toute l'interface via MaterialTheme.
 */
@Composable
fun AssistantJarvisTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CouleursFoncees,
        typography = Typography(), // Tu peux personnaliser plus tard
        content = content
    )
}