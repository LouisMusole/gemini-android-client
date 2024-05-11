package com.drcmind.geminiandroidclient

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val uiState = mutableStateOf(UiState())

    fun getResponseFrom(prompt : String){
        viewModelScope.launch {
            uiState.value = uiState.value.copy(
                isGenerating = true
            )
            val response = AiSource.generationModel.generateContent(prompt).text.toString()
            uiState.value = uiState.value.copy(
                isGenerating = false,
                response = response
            )
        }
    }

    fun restoreState(){
        uiState.value = UiState()
    }

}