package com.example.flicpcardgame

import com.wajahatkarim.flippable.FlippableController

data class CardData(
    var state: CardState,
    val number: Int,
    val id: Int,
    val controller: FlippableController,
)

enum class CardState {
    back,
    front,
    blocked,
}
