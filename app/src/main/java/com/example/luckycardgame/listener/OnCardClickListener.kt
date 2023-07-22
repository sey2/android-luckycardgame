package com.example.luckycardgame.listener

import com.example.luckycardgame.adapter.CardAdapter

interface OnCardClickListener {
    fun onCardClick(cardIndex: Int, holder: CardAdapter.CardViewHolder)
}