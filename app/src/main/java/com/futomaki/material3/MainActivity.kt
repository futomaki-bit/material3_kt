package com.futomaki.material3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.futomaki.material3.ui.theme.Material3Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Material3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Homepage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage() {
    Scaffold(
        topBar = { HomeTopbar() },
        content = { innerPadding -> HomeContent(innerPadding) },
    )
}

@Composable
fun HomeTopbar() {
    LargeTopAppBar(
        title = { Text("Drive or Not") },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeContent(innerPaddingValues: PaddingValues) {
    var input by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var cardList = remember {mutableStateListOf<String>()}
    Column() {
        Spacer(Modifier.height(128.dp))
        Row(Modifier.padding(24.dp)) {
            TextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("distance") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton({
                        cardList.add(input)
                        input = ""
                    }) {
                        Icon(Icons.Filled.PlayArrow, "")
                    }
                }
            )
        }
        Row(Modifier.padding(24.dp, 0.dp)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(items = cardList) {
                    it -> HomeCard(it)
                }
            }
            Spacer(Modifier.height(120.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(distance:String){
    Row() {
        Card(
            modifier = Modifier.width(360.dp)
        ) {
            Box(Modifier.padding(12.dp, 12.dp)) {
                Column() {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column() {
                            Icon(Icons.Filled.Info, "", Modifier.size(48.dp))
                        }
                        Column() {
                            Text("Food", fontWeight = FontWeight.Bold)
                            Text("$1")
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row() {
                        Text("With $distance km you could buy a donut!")
                    }
                }
            }
        }
    }
}