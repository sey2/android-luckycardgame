package com.example.luckycardgame.service

import android.util.Log
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.repository.GameRepository
import kotlin.math.abs

/**
 * CardServiceImpl 클래스는 CardService 인터페이스를 구현합니다.
 */
class GameServiceImpl(
    private val gameRepository: GameRepository,
    private val participantCount: Int
) : GameService {
    /** 카드를 참가자에게 분배하는 메소드입니다. */
    override fun distributeCards() {
        gameRepository.initializeCards(participantCount)
    }

    /** 동일한 카드의 세장을 가진 참가자가 있는지 확인하는 메소드*/
    override fun isTripleMatchPresent(): Boolean {
        val participants = gameRepository.getAllPartripants()

        participants.forEach {
            it.value.cards.forEach { it.number }
        }

        for (participant in participants) {
            val participantCards = participant.value.cards.groupBy { it.number }
            if (participantCards.any { it.value.size >= 3 }) {
                return true
            }
        }
        return false
    }

    /** 참가자 2명이 선택한 카드가 바닥에 있는 카드와 일치하는지 확인하는 메소드입니다. */
    override fun hasTripleMatchWithFloorCard(participant1: String, participant2: String): Boolean {
        val p1Cards = gameRepository.getCardsForParticipant(participant1) ?: return false
        val p2Cards = gameRepository.getCardsForParticipant(participant2) ?: return false
        val floorCardNumber = gameRepository.getFloorCards().randomOrNull()?.number

        return p1Cards.any { it.number == p2Cards.maxByOrNull { it.number }?.number && it.number == floorCardNumber } ||
                p1Cards.any { it.number == p2Cards.minByOrNull { it.number }?.number && it.number == floorCardNumber }
    }

    /** 모든 카드의 속성을 출력하는 메소드입니다. */
    override fun printCardProperties() {
        val cards = gameRepository.getAllCards(false).sortedBy { it.number }

        for (card in cards) {
            card.printProperties(false)
        }
        println()
    }

    /** 참가자가 가진 카드의 속성을 출력하는 메소드입니다. **/
    override fun participantsCardPrint(participantsCards: MutableList<Card>) {
        for (card in participantsCards) {
            card.printProperties(true)
        }
    }

    /** 바닥에 있는 카드를 정렬하는 메소드입니다. **/
    override fun sortFloorCards(reverse: Boolean) {
        var floorCards = gameRepository.getFloorCards()

        if (reverse) {
            val sortedCards = floorCards.sortedByDescending { it.number }.toMutableList()
            gameRepository.setFloorCards(sortedCards)
        } else {
            val sortedCards = floorCards.sortedBy { it.number }.toMutableList()
            gameRepository.setFloorCards(sortedCards)
        }
    }

    /** 참가자의 카드를 정렬하는 메소드입니다.**/
    override fun sortParticipantCards(participant: String, reverse: Boolean) {
        val participantsCards = gameRepository.getCardsForParticipant(participant)

        if (reverse) {
            val sortedCards = participantsCards?.sortedByDescending { it.number }
            gameRepository.setParticipantCards(participant, sortedCards as MutableList<Card>)
        } else {
            val sortedCards = participantsCards?.sortedBy { it.number }
            gameRepository.setParticipantCards(participant, sortedCards as MutableList<Card>)
        }
    }

    override fun findSumAndSubResultSeven(): String {
        val participantIds = gameRepository.getParticipants()

        // 모든 참가자의 collectionSet을 순회
        for (id1 in participantIds) {
            val participant1 = gameRepository.getParticipant(id1)
            val collectionSet1 = participant1.collectionSet

            // 다른 모든 참가자의 collectionSet과 비교
            for (id2 in participantIds) {
                // 동일한 참가자와는 비교하지 않습니다.
                if (id1 == id2) continue

                val participant2 = gameRepository.getParticipant(id2)
                val collectionSet2 = participant2.collectionSet

                // collectionSet1과 collectionSet2에 대해 가능한 모든 조합을 검사
                for (num1 in collectionSet1) {
                    for (num2 in collectionSet2) {
                        if (num1 + num2 == 7 || abs(num1 - num2) == 7) {
                            return "$id1@$id2"
                        }
                    }
                }
            }
        }

        return "none"
    }

    override fun findParticipantWithSeven(): String {
        return gameRepository.getParticipants()
            .firstOrNull { id -> 7 in gameRepository.getParticipant(id).collectionSet } ?: "none"
    }

    override fun findParticipantsWithNoCards(): String {
        return gameRepository.getAllPartripants()
            .filter { it.value.cards.isEmpty() }
            .keys
            .joinToString("@").ifEmpty { "none" }
    }


}

fun Card.printProperties(isTest: Boolean) {
    val emoji = when (this.type) {
        is CardType.Dog -> "\uD83D\uDC36" // 🐶
        is CardType.Cat -> "\uD83D\uDC31" // 🐱
        is CardType.Cow -> "\uD83D\uDC2E" // 🐮
    }

    if (isTest) {
        print("Number: ${this.number} Emoji: $emoji")
    } else {
        Log.d("CardDebug", "Number: ${this.number}, Emoji: $emoji")
    }
}