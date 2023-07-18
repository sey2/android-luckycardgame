package com.example.luckycardgame
import com.example.luckycardgame.repository.GameRepository
import com.example.luckycardgame.repository.GameRepositoryImpl
import com.example.luckycardgame.service.GameService
import com.example.luckycardgame.service.GameServiceImpl
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val gameRepository: GameRepository = GameRepositoryImpl(3)
    val gameService: GameService = GameServiceImpl(gameRepository, 3)

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun cardProperties_print_success() {
        val allCards = cardRepository.getAllCards(false)

        for (card in allCards) {
            assertNotNull(card)
            val emoji = UnicodeUtils.convertToEmoji(card.type)
            println("Number: ${card.number}, Emoji: $emoji")
        }
    }
}
