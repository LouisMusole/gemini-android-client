package com.drcmind.geminiandroidclient

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val uiState = mutableStateOf(UiState())

    fun getResponseFrom(image : Bitmap){
        viewModelScope.launch {
            uiState.value = uiState.value.copy(
                isGenerating = true
            )

            val inputContent = content {
                image(image)
                text("Provide a json representation of fields in this image with following format {'name': '', 'postname': '', 'givenNames': '','dateOfBirth': '','nationality': '','dateOfIssue': '', 'dateOfExpiry': '', 'issuingCountry':'', 'passportNumber':'','sex': '', 'placeOfBirth': '', 'personnelNumber': '', 'placeOfIssue': '', 'placeOfExpiry': '', 'issuingAuthority':''}");
            }

            val response = AiSource.generationModel.generateContent(inputContent)

            val gson = Gson()
            val jsonObject = gson.fromJson(response.text.toString().trimIndent(), Person::class.java)

            Log.d("GEMINIIIII", jsonObject.toString())
            uiState.value = uiState.value.copy(
                isGenerating = false,
                response = jsonObject
            )
        }
    }

    fun restoreState(){
        uiState.value = UiState()
    }

    fun updateSelectedImage(it: Bitmap?) {
        uiState.value = uiState.value.copy(
            selectedImage = it
        )
    }

}