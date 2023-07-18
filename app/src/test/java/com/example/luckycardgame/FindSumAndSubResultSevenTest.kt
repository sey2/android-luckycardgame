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

class FindSumAndSubResultSevenTest{
    private lateinit var gameService: GameServiceImpl
    private lateinit var gameRepository: GameRepository

    @Before
    @Test
    fun setup() {
        gameRepository = Mockito.mock(GameRepository::class.java)
        gameService = GameServiceImpl(gameRepository, 2)
    }

    @Test
    fun findSumAndSubResultSeven_subIsSeven_shouldReturnParticipantId() {
        val card1 = Card(CardType.Dog, 1, 1 , false)
        val card2 = Card(CardType.Cat, 1, 1,false)
        val card3 = Card(CardType.Cow, 1, 1, false)
        val card4 = Card(CardType.Dog, 8, 1 , false)
        val card5 = Card(CardType.Cat, 8, 1,false)
        val card6 = Card(CardType.Cow, 8, 1, false)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(card1, card2, card3), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(card4,card5, card6) ,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "Participant 1@Participant 2")
    }

    @Test
    fun findSumAndSubResultSeven_subIsNotSeven_shouldReturnNone() {
        val card1 = Card(CardType.Dog, 1, 1 , false)
        val card2 = Card(CardType.Cat, 1, 1,false)
        val card3 = Card(CardType.Cow, 1, 1, false)
        val card4 = Card(CardType.Dog, 5, 1 , false)
        val card5 = Card(CardType.Cat, 5, 1,false)
        val card6 = Card(CardType.Cow, 5, 1, false)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(card1, card2, card3), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(card4,card5, card6) ,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "none")
    }

    @Test
    fun findSumAndSubResultSeven_sumIsSeven_shouldReturnParticipantId() {
        val card1 = Card(CardType.Dog, 1, 1 , false)
        val card2 = Card(CardType.Cat, 1, 1,false)
        val card3 = Card(CardType.Cow, 1, 1, false)
        val card4 = Card(CardType.Dog, 6, 1 , false)
        val card5 = Card(CardType.Cat, 6, 1,false)
        val card6 = Card(CardType.Cow, 6, 1, false)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(card1, card2, card3), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(card4,card5, card6) ,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "Participant 1@Participant 2")
    }

    @Test
    fun findSumAndSubResultSeven_sumIsNotSeven_shouldReturnNone() {
        val card1 = Card(CardType.Dog, 1, 1 , false)
        val card2 = Card(CardType.Cat, 1, 1,false)
        val card3 = Card(CardType.Cow, 1, 1, false)
        val card4 = Card(CardType.Dog, 2, 1 , false)
        val card5 = Card(CardType.Cat, 2, 1,false)
        val card6 = Card(CardType.Cow, 2, 1, false)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(card1, card2, card3), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(card4,card5, card6) ,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "none")
    }

}