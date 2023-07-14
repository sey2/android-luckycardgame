package com.example.luckycardgame.model

import com.example.luckycardgame.repository.CardRepository
import com.example.luckycardgame.repository.CardRepositoryImpl
import com.example.luckycardgame.service.CardService
import com.example.luckycardgame.service.CardServiceImpl

class LuckyGame(participantCount: Int) {
    private var cardRepository: CardRepository = CardRepositoryImpl(participantCount)
    val cardService: CardService = CardServiceImpl(cardRepository, participantCount)

    init {
        cardService.distributeCards()
    }

    /**
     * 전체 카드의 개수를 반환합니다.
     *
     * @return 전체 카드의 개수
     */
    fun getTotalCardCount(): Int {
        return cardRepository.getTotalCardCount()
    }

    /**
     * 참가자들 중에 세 장 이상의 같은 번호를 가진 카드가 있는지 확인합니다.
     *
     * @return 세 장 이상의 같은 번호를 가진 카드가 있으면 true, 없으면 false
     */
    fun isTripleMatchPresent(): Boolean {
        return cardService.isTripleMatchPresent()
    }

    /**
     * 바닥에 있는 카드 정렬.
     *
     * @param reverse 역순으로 정렬 할지 여부
     */
    fun sortFloorCards(reverse: Boolean) {
        cardService.sortFloorCards(reverse)
    }

    /**
     * 정해진 참가자의 카드를 번호에 따라 정렬합니다.
     *
     * @param participant 정렬할 참가자의 이름
     * @param reverse 역순으로 정렬 할지 여부
     */
    fun sortParticipantCards(participant: String, reverse: Boolean) {
        cardService.sortParticipantCards(participant, reverse)
    }

    /**
     * 특정 참가자와 다른 참가자의 가장 높은 숫자 카드 또는 가장 낮은 숫자와 바닥 카드 중 아무거나를 선택해서 3개가 같은지 판단합니다.
     *
     * @param participant1 첫 번째 참가자 이름
     * @param participant2 두 번째 참가자 이름
     * @return 3개의 같은 숫자가 있는 경우 true, 그렇지 않은 경우 false
     */
    fun hasTripleMatchWithFloorCard(participant1: String, participant2: String): Boolean {
        return cardService.hasTripleMatchWithFloorCard(participant1, participant2)
    }

    /**
     * 바닥 카드 리스트를 반환합니다.
     *
     * @return 바닥 카드 리스트
     */
    fun getFloorCards(): List<Card> {
        return cardRepository.getFloorCards()
    }

    /**
     * 특정 참가자의 카드 리스트를 반환합니다.
     *
     * @param participant 참가자의 이름
     * @return 참가자의 카드 리스트
     */
    fun getParticipantCards(participant: String): List<Card>? {
        return cardRepository.getCardsForParticipant(participant)
    }

    /**
     * 참가자들의 이름 집합을 반환합니다.
     *
     * @return 참가자들의 이름 집합
     */
    fun getParticipants(): Set<String> {
        return cardRepository.getParticipants()
    }
}
