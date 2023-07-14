package com.example.luckycardgame.service

import android.util.Log
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.repository.CardRepository

/**
 * CardServiceImpl 클래스는 CardService 인터페이스를 구현합니다.
 */
class CardServiceImpl(private val cardRepository: CardRepository, private val participantCount: Int) : CardService {
    /** 카드를 참가자에게 분배하는 메소드입니다. */
    override fun distributeCards() {
        cardRepository.initializeCards(participantCount)
    }

    /** 동일한 카드의 세장을 가진 참가자가 있는지 확인하는 메소드*/
    override fun isTripleMatchPresent(): Boolean {
        val participants = cardRepository.getAllPartripants()

        participants.forEach{
            it.value.cards.forEach {it.number }
        }

        for (participant in participants) {
            val participantCards =   participant.value.cards.groupBy { it.number }
            if (participantCards.any { it.value.size >= 3 }) {
                return true
            }
        }
        return false
    }

    /** 참가자 2명이 선택한 카드가 바닥에 있는 카드와 일치하는지 확인하는 메소드입니다. */
    override fun hasTripleMatchWithFloorCard(participant1: String, participant2: String): Boolean {
        val p1Cards = cardRepository.getCardsForParticipant(participant1) ?: return false
        val p2Cards =  cardRepository.getCardsForParticipant(participant2) ?: return false
        val floorCardNumber = cardRepository.getFloorCards().randomOrNull()?.number

        return p1Cards.any { it.number == p2Cards.maxByOrNull { it.number }?.number && it.number == floorCardNumber } ||
                p1Cards.any { it.number == p2Cards.minByOrNull { it.number }?.number && it.number == floorCardNumber }
    }

    /** 모든 카드의 속성을 출력하는 메소드입니다. */
    override fun printCardProperties() {
        val cards = cardRepository.getAllCards(false).sortedBy { it.number }

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
        var floorCards = cardRepository.getFloorCards()

        if (reverse) {
            val sortedCards =  floorCards.sortedByDescending { it.number }.toMutableList()
            cardRepository.setFloorCards(sortedCards)
        } else {
            val sortedCards =  floorCards.sortedBy { it.number }.toMutableList()
            cardRepository.setFloorCards(sortedCards)
        }
    }

    /** 참가자의 카드를 정렬하는 메소드입니다.**/
    override fun sortParticipantCards(participant: String, reverse: Boolean) {
        val participantsCards = cardRepository.getCardsForParticipant(participant)

        if (reverse) {
            val sortedCards =  participantsCards?.sortedByDescending { it.number }
            cardRepository.setParticipantCards(participant, sortedCards as MutableList<Card>)
        } else {
            val sortedCards = participantsCards?.sortedBy { it.number }
            cardRepository.setParticipantCards(participant, sortedCards as MutableList<Card>)
        }
    }

}
fun Card.printProperties(isTest: Boolean) {
    val emoji = when (this.type) {
        is CardType.Dog -> "\uD83D\uDC36" // 🐶
        is CardType.Cat -> "\uD83D\uDC31" // 🐱
        is CardType.Cow -> "\uD83D\uDC2E" // 🐮
    }

    if(isTest) {
        print("Number: ${this.number} Emoji: $emoji", )
    }else{
        Log.d("CardDebug","Number: ${this.number}, Emoji: $emoji")
    }
}