package com.example.luckycardgame.repository

import com.example.luckycardgame.R
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType

class CardRepositoryImpl : CardRepository {
    override fun getAllCards(isBack: Boolean): MutableList<Card>
        = (1..12).flatMap { number -> listOf(CardType.Dog, CardType.Cat, CardType.Cow)
                       .map { type -> Card(type, number, R.drawable.logo, isBack) } }
                       .shuffled().toMutableList()

}