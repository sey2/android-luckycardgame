package com.example.luckycardgame.repository

import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import kotlin.random.Random

class CardRepositoryImpl : CardRepository {
    override fun getAllCards(): MutableList<Card> =
        (1..12).map {
            val randomType = when (Random.nextInt(0, 3)) {
                0 -> CardType.Dog
                1 -> CardType.Cat
                else -> CardType.Cow
            }
            Card(randomType, it)
        }.toMutableList()
}