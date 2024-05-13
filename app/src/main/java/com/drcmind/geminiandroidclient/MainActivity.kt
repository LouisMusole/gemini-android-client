package com.drcmind.geminiandroidclient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.drcmind.geminiandroidclient.ui.theme.GeminiAndroidClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeminiAndroidClientTheme {

                val viewModel : MainViewModel by viewModels()
                val uiState = viewModel.uiState.value
                
                val photoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {

                    val inputStream = contentResolver.openInputStream(it!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    viewModel.updateSelectedImage(bitmap)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp)) {


                        Column (modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){


                            AsyncImage(
                                model = uiState.selectedImage,
                                placeholder = painterResource(id = R.drawable.placeholder),
                                contentDescription = null,
                                error = painterResource(id = R.drawable.placeholder),
                                modifier = Modifier
                                    .size(200.dp)
                                    .clickable {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    },
                                contentScale = ContentScale.Crop
                            )

                            Button(onClick = {
                                if (uiState.response==null) viewModel.getResponseFrom(uiState.selectedImage!!)
                                else {
                                    viewModel.restoreState()
                                }
                            },
                                enabled = uiState.selectedImage!=null
                                ) {
                                Text(
                                    text = if (uiState.response==null) "Lire"
                                    else "Réinitialiser")
                            }
                        }


                        
                        if(uiState.isGenerating){
                            LinearProgressIndicator(Modifier.fillMaxWidth())
                        }
                        
                        AnimatedVisibility(visible = uiState.response!=null) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())) {
                                Column {
                                    Text(text = "Numéro passport : "+ uiState.response?.passportNumber, modifier = Modifier.padding(16.dp))
                                    Text(text = "Nom : "+ uiState.response?.name, modifier = Modifier.padding(8.dp))
                                    Text(text = "Postnom : "+ uiState.response?.postname, modifier = Modifier.padding(8.dp))
                                    Text(text = "Prénom : "+ uiState.response?.givenNames, modifier = Modifier.padding(8.dp))
                                    Text(text = "Sexe : "+ uiState.response?.sex, modifier = Modifier.padding(8.dp))
                                    Text(text = "Nationalité : "+ uiState.response?.nationality, modifier = Modifier.padding(8.dp))
                                    Text(text = "Date de naissance : "+ uiState.response?.dateOfBirth, modifier = Modifier.padding(8.dp))
                                    Text(text = "Lieu de naissance : "+ uiState.response?.placeOfBirth, modifier = Modifier.padding(8.dp))
                                    Text(text = "Date de soumission : "+ uiState.response?.dateOfIssue, modifier = Modifier.padding(8.dp))
                                    Text(text = "Date d'expiration : "+ uiState.response?.dateOfExpiry, modifier = Modifier.padding(8.dp))
                                    Text(text = "Autorité de soummission : "+ uiState.response?.issuingAuthority, modifier = Modifier.padding(8.dp))
                                    Text(text = "Pays de soumission : "+ uiState.response?.issuingCountry, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
