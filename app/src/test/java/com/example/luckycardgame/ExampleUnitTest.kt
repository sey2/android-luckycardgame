package com.example.luckycardgame

import com.example.luckycardgame.repository.CardRepository
import com.example.luckycardgame.repository.CardRepositoryImpl
import com.example.luckycardgame.utils.UnicodeUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val cardRepository: CardRepository = CardRepositoryImpl(5)
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun cardProperties_print_success() {
        val allCards = cardRepository.getAllCards(false)

        for (card in allCards) {
            assertNotNull(card)
            val emoji = UnicodeUtils.convertToEmoji(card.type)
            println("Number: ${card.number}, Emoji: $emoji")
        }
    }
}