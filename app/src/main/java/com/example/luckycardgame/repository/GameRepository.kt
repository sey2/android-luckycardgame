package com.example.luckycardgame.repository;

import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.Participant


interface GameRepository {
    fun initializeCards(participantCount: Int)

    fun getAllCards(isBack: Boolean): MutableList<Card>

    fun getCardsForParticipant(participant: String): MutableList<Card>

    fun getFloorCards(): MutableList<Card>

    fun getTotalCardCount(): Int

    fun getParticipants(): Set<String>

    fun getAllPartripants(): MutableMap<String, Participant>

    fun setFloorCards(floorCards : MutableList<Card>)

    fun setParticipantCards(participant: String, sortedParticipantCard: MutableList<Card>)

    fun getParticipant(participantId: String): Participant

    fun setParticipant(participantId: String, participant: Participant)

    fun getTripleSelectedCards(): Map<String, List<Int>>

    fun getResultCardSet(): MutableMap<String, Participant>

    fun setResultCardSet()

}