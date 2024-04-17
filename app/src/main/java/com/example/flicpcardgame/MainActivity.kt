package com.example.flicpcardgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flicpcardgame.ui.theme.FlicpCardGameTheme
import com.wajahatkarim.flippable.FlippableController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlicpCardGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameUi()
                }
            }
        }
    }
}

@Composable
fun GameUi() {
    val gameState = remember { mutableStateMapOf<Int, CardData>() }
    val firstCard = remember { mutableStateOf<Int?>(null) }

    val myList = mutableListOf<Int>()
    (1..6).map {
        myList.addAll(listOf(it, it))
    }
    myList.shuffle()
    for (i in 0..<myList.size) {
        gameState[i] =
            CardData(state = CardState.back, number = myList[i], id = i, FlippableController())
    }


    fun onClick(card: CardData?) {
        if (card != null) {
            if (card.state == CardState.blocked || card.state == CardState.front) {
                return
            }
            if (firstCard.value == null) {
                gameState[card.id]?.state = CardState.front
                firstCard.value = card.id
                gameState[card.id]?.controller?.flipToBack()
            } else if (gameState[firstCard.value]?.number == card.number) {
                gameState[card.id]?.state = CardState.blocked
                gameState[firstCard.value]?.state = CardState.blocked
                firstCard.value = null
                gameState[card.id]?.controller?.flipToBack()

            } else {
                gameState[card.id]?.controller?.flipToBack()
                gameState[card.id]?.state = CardState.back
                gameState[firstCard.value]?.state = CardState.back // Delay for 2 seconds
                gameState[card.id]?.controller?.flipToFront()
                gameState[firstCard.value]?.controller?.flipToFront()
                firstCard.value = null
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(82.dp),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(myList.size) { index ->
                GameCard(
                    card = gameState[index],
                    onClick = { onClick(gameState[index]) })
            }
        }
    )
}



