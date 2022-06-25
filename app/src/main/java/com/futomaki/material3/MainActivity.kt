package com.futomaki.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.futomaki.material3.ui.theme.Material3Theme
import kotlin.math.roundToInt


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
    var cardList = remember { mutableStateListOf<String>() }
    Scaffold(
        topBar = { HomeTopbar() },
        content = { innerPadding ->
            HomeContent(innerPadding, cardList)
        },
        bottomBar = {
            HomeBottombar(cardList)
        }
    )
}

@Composable
fun HomeTopbar() {
    LargeTopAppBar(
        title = { Text("Drive or Not") },
        actions = {
            IconButton(onClick = { /* TODO dropdown */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "")
            }
        }
    )
}

@Composable
fun HomeContent(innerPaddingValues: PaddingValues, cardList: MutableList<String>) {
    Box(modifier = Modifier.padding(innerPaddingValues)) {
        Column() {
            Row(Modifier.padding(24.dp, 0.dp)) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(items = cardList) { it ->
                        HomeCard(it)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(distance: String) {

    var dollar = (distance.toInt()*2.1*10/100).roundToInt()
    var price = ((distance.toInt()*2.1*10/100)* 100.0).roundToInt() / 100.0
    Row() {
        Card(
            modifier = Modifier.width(360.dp)
        ) {
            Box(Modifier.padding(12.dp, 12.dp)) {
                Column() {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column() {
                            Icon(Icons.Filled.Info, "", Modifier.size(48.dp))
                        }
                        Column() {
                            Text(
                                "${FoodList(dollar).second.replaceFirstChar(Char::titlecase)}",
                                fontWeight = FontWeight.Bold
                            )
                            // TODO in settings
                            // price = distance * gas price * consumption/100
                            // $ = km * $/L * L/100km * 1/100
                            Text("$distance km")
                        }
                    }
                    Spacer(Modifier.height(5.dp))

                    Row() {
                        Text(
                            // TODO in settings
                            // price = distance * gas price * consumption/100
                            // $ = km * $/L * L/100km * 1/100
                            // The portion '* 100.0).roundToInt() / 100.0' is to leave 2 decimal places
                            "With $price km you could buy ${FoodList(dollar).first} ${
                                FoodList(dollar).second}!"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeBottombar(cardList: MutableList<String>) {
    var input by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(Modifier.padding(24.dp,12.dp)
    ) {
        TextField(
            value = input,
            onValueChange = { value ->
                input = value.filter { it.isDigit() }
            },
            label = { Text("distance") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    keyboardController?.hide()
                    if (input != "") {
                        cardList.add(input)
                    }
                    input = ""

                }
            ),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton({
                    if (input != "") {
                        cardList.add(input)
                    }
                    input = ""
                }) {
                    Icon(Icons.Filled.PlayArrow, "")
                }
            }
        )
    }
}

fun FoodList(cost: Int): Pair<String, String> {
    return when (cost) {
        1 -> Pair("a", "candy")
        2 -> Pair("a", "donut")
        else -> Pair("just", "nothing")
        // TODO add more items
    }
}