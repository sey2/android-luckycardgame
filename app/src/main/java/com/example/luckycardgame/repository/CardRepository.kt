package com.example.luckycardgame.repository;

import com.example.luckycardgame.model.Card


interface CardRepository {
        fun getAllCards(): MutableList<Card>
}
