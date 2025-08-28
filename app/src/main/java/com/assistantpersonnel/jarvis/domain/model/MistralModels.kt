package com.assistantpersonnel.jarvis.domain.model
data class Message(
    val role: String,
    val content: String
)

data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double,
    val top_p: Double,
    val max_tokens: Int
)

data class MistralResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
