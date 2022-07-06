package com.futomaki.material3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settingspage() {
    var cardList = remember { mutableStateListOf<String>() }
    Scaffold(
        topBar = { SettingsTopbar() },
        content = { innerPadding ->
            SettingsContent(innerPadding)
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsContent(innerPadding: PaddingValues) {
    // car consumption, km / L
    var consumption by remember { mutableStateOf("")}
    // gas price
    var gasprice by remember { mutableStateOf("")}
    // keyboard controller to request hide keyboard
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        Modifier.padding(innerPadding),
    ) {
        Column(
            Modifier.fillMaxWidth().fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.Center
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)){
                Row(
                    Modifier
                        .padding(24.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = consumption,
                        onValueChange = {consumption = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done),
                        label = {Text("Car Consumption")},
                        placeholder = {Text("L/km")},
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        )
                    )
                }
                Row(
                    Modifier
                        .padding(24.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = gasprice,
                        onValueChange = {gasprice = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done),
                        label = {Text("Gas Price")},
                        placeholder = {Text("$/L")},
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        )
                    )
                }
                Row(
                    Modifier
                        .padding(24.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text("Send")
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsTopbar(){
    LargeTopAppBar(
        title = { Text("Settings") },
        navigationIcon = {
            IconButton(onClick={/* TODO go back to home*/} ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "")
            }
        }
    )
}