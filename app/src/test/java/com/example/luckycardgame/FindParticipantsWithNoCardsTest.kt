package com.example.luckycardgame

import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.model.Participant
import com.example.luckycardgame.repository.GameRepository
import com.example.luckycardgame.service.GameServiceImpl
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class FindParticipantsWithNoCardsTest {
    private lateinit var gameService: GameServiceImpl
    private lateinit var gameRepository: GameRepository
    private lateinit var participant1: Participant
    private lateinit var participant2: Participant
    private lateinit var participant3: Participant

    @Before
    @Test
    fun setup() {
        gameRepository = mock(GameRepository::class.java)
        gameService = GameServiceImpl(gameRepository, 3)

        participant1 = Participant("Participant 1",
            mutableListOf(),
            hashSetOf(1, 2, 3),
            0,7,0)

        participant2 = Participant("Participant 2",
            mutableListOf(Card(CardType.Dog, 4, 1, false),
                Card(CardType.Dog, 5, 1, false),
                Card(CardType.Dog, 6, 1, false),
                Card(CardType.Dog, 7, 1, false),
                Card(CardType.Dog, 8, 1, false),
                Card(CardType.Dog, 9, 1, false)),
            hashSetOf(),
            0,7,0)

        participant3 = Participant("Participant 3",
            mutableListOf(Card(CardType.Dog, 4, 1, false),
                Card(CardType.Dog, 5, 1, false),
                Card(CardType.Dog, 6, 1, false),
                Card(CardType.Dog, 7, 1, false),
                Card(CardType.Dog, 8, 1, false),
                Card(CardType.Dog, 9, 1, false)),
            hashSetOf(1, 2, 3),
            0,7,0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2", "Participant 3"))
        Mockito.`when`(gameRepository.getAllPartripants()).thenReturn(
            mutableMapOf(Pair("Participant 1", participant1),Pair("Participant 2", participant2), Pair("Participant 3", participant3)))


    }

    @Test
    fun findParticipantsWithNoCards_NoOneParticipantWithoutCards_shouldReturnParticipantId() {
        val result = gameService.findParticipantsWithNoCards()

        assertEquals("Participant 1", result)
    }

    @Test
    fun findParticipantsWithNoCards_NoTwoParticipantWithoutCards_shouldReturnParticipantIds() {
        participant2.cards.clear()
        val result = gameService.findParticipantsWithNoCards()

        assertEquals("Participant 1@Participant 2", result)
    }

    @Test
    fun findParticipantsWithNoCards_NoThreeParticipantWithoutCards_shouldReturnParticipantIds() {
        participant2.cards.clear()
        participant3.cards.clear()
        val result = gameService.findParticipantsWithNoCards()

        assertEquals("Participant 1@Participant 2@Participant 3", result)
    }

    @Test
    fun findParticipantsWithNoCards_AllParticipantWithoutCardsExist_shouldReturnNone() {
        participant1.cards.add(Card(CardType.Dog, 8, 1, false))

        val result = gameService.findParticipantsWithNoCards()

        assertEquals("none", result)
    }
}