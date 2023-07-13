package com.example.luckycardgame.service

import android.util.Log
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.repository.CardRepository

class CardServiceImpl(private val cardRepository: CardRepository) : CardService {
    override fun printCardProperties() {
        val cards = cardRepository.getAllCards(false).sortedBy { it.number }

        for (card in cards) {
            card.printProperties()
        }
    }
}

fun Card.printProperties() {
    val emoji = when (this.type) {
        is CardType.Dog -> "\uD83D\uDC36" // 🐶
        is CardType.Cat -> "\uD83D\uDC31" // 🐱
        is CardType.Cow -> "\uD83D\uDC2E" // 🐮
    }

    Log.d("Card", "Number: ${this.number}, Emoji: $emoji")
}