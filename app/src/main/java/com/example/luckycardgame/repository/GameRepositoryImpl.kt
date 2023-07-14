package com.example.luckycardgame.repository

import com.example.luckycardgame.R
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.model.Participant

/**
 * CardRepositoryImpl 클래스는 CardRepository 인터페이스를 구현합니다.
 * 주요 변수는 게임에 사용할 모든 카드, 참가자별로 카드를 저장하는 맵, 바닥에 놓인 카드를 저장하는 리스트입니다.
 */

class GameRepositoryImpl(private val participantCount: Int) : GameRepository {
    private lateinit var allCards: MutableList<Card>
    private var participants: MutableMap<String, Participant> = mutableMapOf()
    private var resultCardSet: MutableMap<String, Participant> = mutableMapOf()

    private var floorCards: MutableList<Card> = mutableListOf()
    override fun initializeCards(participantCount: Int) {
        val rawCards = getAllCards(false)
        allCards = mutableListOf()

        val participantCardCounts = when (participantCount) {
            3 -> 8
            4 -> 7
            5 -> 6
            else -> throw IllegalArgumentException("Invalid participant count: $participantCount")
        }

        // 카드를 번호별로 그룹화하고 각 그룹을 섞음
        val groupedCards = rawCards.groupBy { it.number }.values.map { it.shuffled() }
        for (group in groupedCards) {
            allCards.addAll(group)
        }

        for (i in 0 until participantCount) {
            // 새 참가자 생성
            participants["Participant $i"] = Participant("Participant $i", leftSelectIndex = 0, rightSelectIndex = participantCardCounts-1, selectCount =  0)

            for (j in 0 until participantCardCounts) {
                participants["Participant $i"]?.addCard(allCards.removeFirst())
            }
        }

        allCards.forEach { floorCards.add(it) }

        allCards.clear()
    }
    override fun getAllCards(isBack: Boolean): MutableList<Card> {
        val cards = mutableListOf<Card>()
        val cardTyps = listOf(CardType.Dog, CardType.Cow, CardType.Cat)
        for (number in 1..12) {
            repeat(3) {
                cards.add(Card(cardTyps[it], number, R.drawable.logo, isBack))
            }
        }

        val shuffledCards = cards.shuffled().toMutableList()

        if (participantCount == 3)
            shuffledCards.removeAll { card -> card.number == 12 }

        return shuffledCards
    }
    override fun getCardsForParticipant(participant: String): MutableList<Card> {
        return participants[participant]?.cards ?: mutableListOf()
    }

    override fun getAllPartripants(): MutableMap<String, Participant> {
        return participants
    }

    override fun getFloorCards(): MutableList<Card> {
        return floorCards
    }

    override fun getTotalCardCount(): Int {
        return participants.values.sumOf { it.cards.size }
    }

    override fun getParticipants(): Set<String> {
        return participants.keys
    }

    override fun getParticipant(participantId: String): Participant {
        return participants[participantId] ?: throw Error("The participant ID could not be found.")
    }

    override fun setFloorCards(sortedfloorCards: MutableList<Card>) {
        this.floorCards = sortedfloorCards
    }

    override fun setParticipantCards(
        participant: String,
        sortedParticipantCard: MutableList<Card>
    ) {
        participants[participant]!!.cards = sortedParticipantCard
    }

    override fun setParticipant(participantId: String, participant: Participant) {
        participants[participantId] = participant
    }

    private fun getRandomCardType(): CardType =
        listOf(CardType.Dog, CardType.Cat, CardType.Cow).random()

    override fun getTripleSelectedCards(): Map<String, List<Int>> {
        val triples = mutableMapOf<String, List<Int>>()

        for ((key, value) in participants) {

            // 참가자의 선택한 카드 리스트를 가져옴
            val participantSelectedCards = value.selectedCard.map { value.cards[it] }

            // 카드 번호 별로 그룹화
            val groupedByNumber = participantSelectedCards.groupBy { it.number }

            // 3장 이상인 카드 그룹 찾기
            val tripleCardNumbers = groupedByNumber.filter { it.value.size >= 3 }.keys.toList()

            if (tripleCardNumbers.isNotEmpty()) {
                triples[key] = tripleCardNumbers
            }
        }
        return triples
    }

    override fun getResultCardSet(): MutableMap<String, Participant> {
        return resultCardSet
    }

    override fun setResultCardSet() {

    }
}
