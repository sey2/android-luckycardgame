package com.example.luckycardgame.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.luckycardgame.R
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import java.time.LocalTime
import kotlin.random.Random

class CardRepositoryImpl : CardRepository {
    override fun getAllCards(isBack: Boolean): MutableList<Card>
        = (1..12).flatMap { number -> listOf(CardType.Dog, CardType.Cat, CardType.Cow)
                       .map { type -> Card(type, number, R.drawable.logo, isBack) } }
                       .shuffled().toMutableList()

}