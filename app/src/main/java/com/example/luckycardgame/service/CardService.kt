package com.example.luckycardgame.service

import com.example.luckycardgame.model.Card

interface CardService {
    /**
     * 카드의 속성을 출력하는 함수.
     */
    fun printCardProperties()

    /**
     * 참가자의 카드를 출력하는 함수.
     *
     * @param participantsCards 참가자의 카드 리스트
     */
    fun participantsCardPrint(participantsCards: MutableList<Card>)

    /**
     * 카드를 분배하는 함수.
     */
    fun distributeCards()

    /**
     * 세 장 이상의 같은 숫자를 가진 카드가 있는지 확인하는 함수.
     *
     * @return 세 장 이상의 같은 숫자를 가진 카드가 있으면 true, 없으면 false
     */
    fun isTripleMatchPresent(): Boolean

    /**
     * 특정 참가자와 다른 참가자의 가장 높은 숫자 카드 또는 가장 낮은 숫자와 바닥 카드 중 아무거나를 선택해서 3개가 같은지 판단하는 함수.
     *
     * @param participant1 첫 번째 참가자 이름
     * @param participant2 두 번째 참가자 이름
     * @return 3개의 같은 숫자가 있는 경우 true, 그렇지 않은 경우 false
     */
    fun hasTripleMatchWithFloorCard(participant1: String, participant2: String): Boolean


    /**
     * 정해진 참가자의 카드를 번호에 따라 정렬하는 함수.
     *
     * @param participant 정렬할 참가자의 이름
     * @param reverse 역순으로 정렬 할지 여부
     */
    fun sortParticipantCards(participant: String, reverse: Boolean)

    /**
     * 바닥에 있는 카드를 정렬하는 함수.
     *
     * @param reverse 역순으로 정렬 할지 여부
     */
    fun sortFloorCards(reverse: Boolean)


}