package ru.midhey.android_labs

import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.midhey.android_labs.ui.theme.Android_labsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_labsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            First(navController)
                        }
                        composable("second") {
                            Second(navController)
                        }
                        composable("third-main") {
                            Third(navController)
                        }
                        composable("third-optional/{count}", arguments= listOf(
                            navArgument("count") {
                                type = NavType.IntType
                            }
                        )) {
                            val count = it.arguments?.getInt("count") ?:0
                            ThirdOpt(count = count)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun First(navController: NavController) {
    val context = LocalContext.current
    Column(
        content = {
            Button(onClick = {
                Toast.makeText(
                    context,
                    "Тосты готовы",
                    Toast.LENGTH_SHORT
                ).show()
            }, content = {
                Text(text = "Кнопка")
            })
            IconButton(onClick = { navController.navigate("second") }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null
                )
            }
        }, modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Second(navController: NavController) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val (text, onTextChange) = remember { mutableStateOf("")}
        TextField(value = text, onValueChange = {
            onTextChange(it)
        })
        val (newText, onNewTextChange) = remember { mutableStateOf("") }
        Button(onClick = { onNewTextChange(text)  }) {
            Text(text = "Кнопка")
        }
        Text(text = newText)
        IconButton(onClick = { navController.navigate("third-main") }) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Third(navController: NavController) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val (numbers, onNumbersChange) = remember { mutableStateOf("") }
        TextField(value = numbers, onValueChange = { it ->
            onNumbersChange(Regex("[^0-9]").replace(it , "" ))
        })
        Button(onClick = { navController.navigate("third-optional/$numbers") }) {
            Text(text = "Перейти")
        }
    }
}

@Composable
fun ThirdOpt(count: Int) {
    Column {
        for (n in 1..count) {
            Text("Пишу текст")
        }
    }
}