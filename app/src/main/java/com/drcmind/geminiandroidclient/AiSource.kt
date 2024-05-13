package com.drcmind.geminiandroidclient

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting

object AiSource {
    val API_Key = "AIzaSyAPaQUBnTS49fIpKjy0S1-zCHYpfQOBHaM"

    val generationModel = GenerativeModel(modelName = "gemini-1.5-pro-latest", apiKey = API_Key, safetySettings = listOf(
        SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
        SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
        SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
        SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
    ))
}