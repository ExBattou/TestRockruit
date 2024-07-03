package com.strayfox.testrockruit

import CompassViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strayfox.testrockruit.ui.theme.TestRockruitTheme

class MainActivity : ComponentActivity() {
    private val viewModel: CompassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRockruitTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    val characters = viewModel.characters.observeAsState()
                    val words = viewModel.words.observeAsState()

                    Column {
                        Button(
                            onClick = { viewModel.fetchData() },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Fetch Data")
                        }
                        Row {
                            characters.value?.let {
                                showEveryCharacter(it)
                            }
                            words.value?.let { wordArray ->
                                showWordCount(strings = wordArray)
                            }
                        }
                        }

                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }


    @Composable
    fun showEveryCharacter(charArray: Array<Char>) {
        LazyColumn {
            items(charArray) { char ->
                CharItem(char)
            }
        }
    }

    @Composable
    fun CharItem(char: Char) {
        Text(
            text = char.toString(),
            Modifier
                .padding(30.dp,3.dp,30.dp,3.dp),
            style = TextStyle(
                fontSize = 24.sp,
            )
        )
    }

    @Composable
    fun showWordCount(strings: Array<String>) {
        LazyColumn {
            items(strings) { string ->
                StringItem(string)
            }
        }
    }

    @Composable
    fun StringItem(string: String) {
        val offset = Offset(5.0f, 10.0f)
        Text(
            text = string,
            style = TextStyle(
                fontSize = 18.sp,
            )
        )
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TestRockruitTheme {
            Greeting("Android")
        }
    }
}