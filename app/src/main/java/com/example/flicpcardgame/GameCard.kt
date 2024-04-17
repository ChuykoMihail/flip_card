package com.example.flicpcardgame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController


@Composable
fun GameCard(card: CardData?, onClick: (card: CardData?) -> Unit) {

    Flippable(
        flipEnabled = card?.state == CardState.back,
        modifier = Modifier
            .padding(vertical = 16.dp),
        backSide = {
            Card(modifier = Modifier.size(64.dp, 64.dp).clickable { onClick(card) }) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "${card?.number}", modifier = Modifier
                                .align(Alignment.Center), textAlign = TextAlign.Center
                        )
                    })
            }
        },
        frontSide = {
            Card(modifier = Modifier.size(64.dp, 64.dp).clickable { onClick(card) }) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "#", modifier = Modifier
                                .align(Alignment.Center), textAlign = TextAlign.Center
                        )
                    })
            }
        },
        flipController = card?.controller ?: remember {
            FlippableController()
        },
        autoFlip = false
    )
}