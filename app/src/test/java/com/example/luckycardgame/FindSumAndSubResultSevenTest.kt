package com.example.luckycardgame

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
        val participant1 = Participant("Participant 1", collectionSet = setOf(3,5) as HashSet<Int>, leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = setOf(10,11) as HashSet<Int>,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "Participant 1@Participant 2")
    }

    @Test
    fun findSumAndSubResultSeven_subIsNotSeven_shouldReturnContinue() {
        val participant1 = Participant("Participant 1", collectionSet = setOf(2,5) as HashSet<Int>, leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = setOf(10,11) as HashSet<Int>,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "none")
    }

    @Test
    fun findSumAndSubResultSeven_sumIsSeven_shouldReturnParticipantId() {
        val participant1 = Participant("Participant 1", collectionSet = setOf(1,5) as HashSet<Int>, leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = setOf(6,11) as HashSet<Int>,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "Participant 1@Participant 2")
    }

    @Test
    fun findSumAndSubResultSeven_sumIsNotSeven_shouldReturnTrue() {
        val participant1 = Participant("Participant 1", collectionSet = setOf(1,5) as HashSet<Int>, leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = setOf(9,11) as HashSet<Int>,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        Mockito.`when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        Mockito.`when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        Mockito.`when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        val result = gameService.findSumAndSubResultSeven()
        assertEquals(result, "none")
    }

}