package com.example.luckycardgame.model
class Participant(
    val name: String,
    var cards: MutableList<Card> = mutableListOf(),
    var selectedCard: HashSet<Int> = HashSet(),
    var leftSelectIndex: Int,
    var rightSelectIndex: Int,
    var selectCount: Int,
    var collectionSet: ArrayList<Card> = ArrayList<Card>()
) {
    fun addCard(card: Card) {
        cards.add(card)
    }
    fun addSelectedCard(index: Int){
        selectedCard.add(index)
    }

    fun removeSelectedCard(cardNum: Int){
        for(i in 0 until cards.size){
            if(cards[i].number == cardNum) {
                selectedCard.remove(i)

                leftSelectIndex = if(i == leftSelectIndex-1) 0 else leftSelectIndex
            }
        }
    }

    fun removeAllCards() {
        cards.clear()
    }
}