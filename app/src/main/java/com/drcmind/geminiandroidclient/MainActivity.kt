package com.drcmind.geminiandroidclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drcmind.geminiandroidclient.ui.theme.GeminiAndroidClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeminiAndroidClientTheme {

                val viewModel : MainViewModel by viewModels()
                val uiState = viewModel.uiState.value

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp)) {

                        var prompt by remember {
                            mutableStateOf("")
                        }

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = prompt,
                            onValueChange = {prompt = it},
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (uiState.response.isEmpty()) viewModel.getResponseFrom(prompt)
                                    else {
                                        viewModel.restoreState()
                                        prompt = ""
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (uiState.response.isEmpty()) Icons.Filled.Check
                                        else Icons.Filled.Clear, 
                                        contentDescription = null)
                                }
                            }
                        )
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        if(uiState.isGenerating){
                            LinearProgressIndicator(Modifier.fillMaxWidth())
                        }
                        
                        AnimatedVisibility(visible = uiState.response.isNotEmpty()) {
                            Card(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                Text(text = uiState.response, modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
