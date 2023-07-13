package com.example.luckycardgame.model

import com.example.luckycardgame.repository.GameRepository
import com.example.luckycardgame.repository.GameRepositoryImpl
import com.example.luckycardgame.service.GameService
import com.example.luckycardgame.service.GameServiceImpl

class LuckyGame(participantCount: Int) {
    private var gameRepository: GameRepository = GameRepositoryImpl(participantCount)
    val service: GameService = GameServiceImpl(gameRepository, participantCount)
    var currentPlayerId = "Participant 0"
    var turn = 1

    init {
        service.distributeCards()
        gameRepository.getParticipants().forEach { name -> service.sortParticipantCards(name, false) }
    }

    /**
     * 특정 참가자가 카드를 선택하는 메소드입니다.
     *
     * @param participantName 참가자의 이름입니다.
     * @param cardIndex 사용자가 선택한 카드의 인덱스입니다.
     * @return 사용자가 선택한 카드를 집을 수 있으면 true, 집을 수 없으면 false 반환
     */
    fun canSelected(participantName: String, cardIndex: Int): Boolean {
        val participant = gameRepository.getParticipant(participantName)

        require(cardIndex in 0 until participant.cards.size) { "카드 인덱스가 범위를 벗어났습니다." }

        val isCanSelectIndex = (participant.leftSelectIndex == cardIndex || participant.rightSelectIndex == cardIndex)

        // 선택한 카드가 이미 선택되었는지 확인
        if (!participant.selectedCard.contains(cardIndex) && isCanSelectIndex)  {
            getParticipant(currentPlayerId).selectCount++
        }

        when (cardIndex) {
            participant.leftSelectIndex ->  participant.leftSelectIndex = cardIndex + 1
            participant.rightSelectIndex -> participant.rightSelectIndex = cardIndex -1
            else -> return false
        }

        participant.addSelectedCard(cardIndex)

        if (participant.selectCount % 3 == 0 )
            gameRepository.setParticipant(participantName, participant)

        return true
    }

    fun turnRest(){
        var tripleCardSet = getParticipantsTripleSelectedCardNum()

        for((participantId, cardList) in tripleCardSet){
            val participant = gameRepository.getParticipant(participantId)

            cardList.forEach { target ->
                // 사용자 선택 카드 리스트에서도 제거
                participant.removeSelectedCard(target)

                // 같은 번호 모은거는 따로 모아놓기
                participant.collectionSet = participant.cards.filter { it.number == target }.map { it.number }.toHashSet()

                // 사용자 카드 리스트에서 같은 번호 모은거는 제거
                participant.cards = participant.cards.filterNot { it.number == target }.toMutableList()
            }
        }

        gameRepository.getParticipants().map{ id ->
            val participant = gameRepository.getParticipant(id)
            participant.selectedCard.clear()
            participant.leftSelectIndex = 0
            participant.rightSelectIndex= participant.cards.size-1
        }
    }

    /**
     * 바닥 카드 리스트를 반환합니다.
     *
     * @return 바닥 카드 리스트
     */
    fun getFloorCards(): List<Card> {
        return gameRepository.getFloorCards()
    }

    /**
     * 특정 참가자의 카드 리스트를 반환합니다.
     *
     * @param participant 참가자의 이름
     * @return 참가자의 카드 리스트
     */
    fun getParticipantCards(participant: String): List<Card>? {
        return gameRepository.getCardsForParticipant(participant)
    }

    /**
     * 참가자들의 이름 집합을 반환합니다.
     *
     * @return 참가자들의 이름 집합
     */
    fun getParticipants(): Set<String> {
        return gameRepository.getParticipants()
    }

    /**
     * 특정 참가자의 객체를 반환 합니다.
     */
    fun getParticipant(participantId: String): Participant {
        return gameRepository.getParticipant(participantId)
    }

    fun getParticipantsTripleSelectedCardNum(): Map<String, List<Int>> {
        return gameRepository.getTripleSelectedCards()
    }
}