package com.example.luckycardgame.utils

import com.example.luckycardgame.model.CardType

object UnicodeUtils {
    fun convertToEmoji(type: CardType) =
        when (type) {
            is CardType.Dog -> "\uD83D\uDC36" // 🐶
            is CardType.Cat -> "\uD83D\uDC31" // 🐱
            is CardType.Cow -> "\uD83D\uDC2E" // 🐮
        }

}
