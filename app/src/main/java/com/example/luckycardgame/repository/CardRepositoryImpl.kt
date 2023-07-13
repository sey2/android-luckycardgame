package com.example.luckycardgame.repository
import com.example.luckycardgame.R
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.model.Participant

/**
 * CardRepositoryImpl 클래스는 CardRepository 인터페이스를 구현합니다.
 * 주요 변수는 게임에 사용할 모든 카드, 참가자별로 카드를 저장하는 맵, 바닥에 놓인 카드를 저장하는 리스트입니다.
 */

class CardRepositoryImpl(private val participantCount: Int) : CardRepository {
    private lateinit var allCards: MutableList<Card>
    private var participants: MutableMap<String, Participant> = mutableMapOf()
    private var floorCards: MutableList<Card> = mutableListOf()

    /**
     * 참가자에게 카드를 분배하고 카드를 초기화하는 메소드입니다.
     * @param participantCount 카드를 받을 참가자의 수입니다.
     */
    override fun initializeCards(participantCount: Int) {
        allCards = getAllCards(false).toMutableList()

        val participantCardCounts = when (participantCount) {
            3 -> 8
            4 -> 7
            5 -> 6
            else -> throw IllegalArgumentException("Invalid participant count: $participantCount")
        }

        for (i in 0 until participantCount) {
            participants["Participant $i"] = Participant("Participant $i") // 새 참가자 생성

            for (j in 0 until participantCardCounts) {
                participants["Participant $i"]?.addCard(allCards.removeFirst())
            }
        }

        allCards.forEach { floorCards.add(it) }

        allCards.clear()
    }

    /**
     * 모든 카드를 가져오는 메소드입니다.
     * @param isBack 카드의 앞면 혹은 뒷면을 선택하는 파라미터입니다.
     * @return isBack에 따른 모든 카드의 리스트를 반환합니다.
     */
    override fun getAllCards(isBack: Boolean): MutableList<Card> {
        val cards = mutableListOf<Card>()

        for (number in 1..12) {
            repeat(3) {
                cards.add(Card(getRandomCardType(), number, R.drawable.logo, isBack))
            }
        }

        val shuffledCards = cards.shuffled().toMutableList()

        if (participantCount == 3)
            shuffledCards.removeAll { card -> card.number == 12 }

        return shuffledCards
    }

    /**
     * 특정 참가자가 가진 카드를 가져오는 메소드입니다.
     * @param participant 카드를 가져올 참가자의 이름입니다.
     * @return 해당 참가자가 가진 카드의 리스트를 반환합니다.
     */
    override fun getCardsForParticipant(participant: String): MutableList<Card> {
        return participants[participant]?.cards ?: mutableListOf()
    }

    /**
     * 모든 참가자들을 반환하는 메소드입니다.
     * @return 모든 참가자들을 반환합니다.
     */
    override fun getAllPartripants(): MutableMap<String, Participant> {
        return participants
    }

    /**
     * 바닥에 놓인 카드를 가져오는 메소드입니다.
     * @return 바닥에 놓인 카드의 리스트를 반환합니다.
     */
    override fun getFloorCards(): MutableList<Card> {
        return floorCards
    }

    /**
     * 모든 카드의 수를 가져오는 메소드입니다.
     * @return 모든 카드의 총 수를 반환합니다.
     */
    override fun getTotalCardCount(): Int {
        return participants.values.sumOf { it.cards.size }
    }

    /**
     * 게임에 참가한 모든 참가자를 가져오는 메소드입니다.
     * @return 참가한 모든 참가자의 이름이 담긴 Set을 반환합니다.
     */
    override fun getParticipants(): Set<String> {
        return participants.keys
    }

    /**
     * 바닥에 놓인 카드를 셋팅하는 메소드입니다.
     * @param sortedfloorCards 새롭게 셋팅할 카드의 리스트입니다.
     */
    override fun setFloorCards(sortedfloorCards: MutableList<Card>) {
        this.floorCards = sortedfloorCards
    }

    /**
     * 참가자의 카드를 셋팅하는 메소드입니다.
     * @param participant 카드를 셋팅할 참가자의 이름입니다.
     * @param sortedParticipantCard 새롭게 셋팅할 카드의 리스트입니다.
     */
    override fun setParticipantCards(
        participant: String,
        sortedParticipantCard: MutableList<Card>
    ) {
        participants[participant]!!.cards = sortedParticipantCard
    }

    /**
     * 랜덤한 카드 타입을 반환하는 메소드입니다.
     * @return Dog, Cat, Cow 중 랜덤하게 선택된 카드 타입을 반환합니다.
     */
    private fun getRandomCardType(): CardType =
        listOf(CardType.Dog, CardType.Cat, CardType.Cow).random()
}