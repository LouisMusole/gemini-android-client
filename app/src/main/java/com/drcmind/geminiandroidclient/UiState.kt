package com.drcmind.geminiandroidclient

import android.graphics.Bitmap

data class UiState(
    val isGenerating : Boolean = false,
    val selectedImage : Bitmap? = null,
    val response : Person? = null
)
