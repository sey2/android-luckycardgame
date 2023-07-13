package com.example.luckycardgame.model

/**
 * Participant 클래스는 게임 참가자를 나타냅니다.
 *
 * @property name 참가자의 이름을 나타냅니다.
 * @property cards 참가자가 가진 카드의 리스트를 나타냅니다.
 */
class Participant(
    val name: String,
    var cards: MutableList<Card> = mutableListOf()
) {
    /**
     * 참가자에게 카드를 추가하는 메소드입니다.
     *
     * @param card 참가자에게 추가될 카드입니다.
     */
    fun addCard(card: Card) {
        cards.add(card)
    }

    /**
     * 참가자가 가진 특정 카드를 제거하는 메소드입니다.
     *
     * @param card 참가자로부터 제거될 카드입니다.
     * @return 카드가 성공적으로 제거되었으면 true, 그렇지 않으면 false를 반환합니다.
     */
    fun removeCard(card: Card): Boolean {
        return cards.remove(card)
    }

    /**
     * 참가자가 가진 카드들을 모두 제거하는 메소드입니다.
     */
    fun removeAllCards() {
        cards.clear()
    }
}
