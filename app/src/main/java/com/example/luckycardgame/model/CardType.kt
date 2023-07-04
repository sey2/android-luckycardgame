package com.example.luckycardgame.model

sealed class CardType {
    object Dog : CardType()
    object Cat : CardType()
    object Cow : CardType()
}
