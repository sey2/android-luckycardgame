package com.example.luckycardgame.`interface`

import com.example.luckycardgame.adapter.CardAdapter

interface OnCardClickListener {
    fun onCardClick(cardIndex: Int, holder: CardAdapter.CardViewHolder)
}