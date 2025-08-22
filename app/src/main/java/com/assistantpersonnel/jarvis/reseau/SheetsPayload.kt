package com.assistantpersonnel.jarvis.reseau

data class SheetsPayload(
    val module: String,
    val ligne: List<String>
)