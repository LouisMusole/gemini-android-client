package com.drcmind.geminiandroidclient

import com.google.ai.client.generativeai.GenerativeModel

object AiSource {
    val API_Key = "AIzaSyAPaQUBnTS49fIpKjy0S1-zCHYpfQOBHaM"

    val generationModel = GenerativeModel(modelName = "gemini-pro", apiKey = API_Key)
}