package com.example.luckycardgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luckycardgame.adapter.CardAdapter
import com.example.luckycardgame.databinding.ActivityMainBinding
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.repository.CardRepository
import com.example.luckycardgame.repository.CardRepositoryImpl
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    private val cardRepository: CardRepository = CardRepositoryImpl()
    private var gameCardList = cardRepository.getAllCards(false)
    private var cardAdapterList: MutableList<CardAdapter> = mutableListOf()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootLayout)

        val recyclerViews = arrayListOf(
            binding.item1Recycler,
            binding.item2Recycler,
            binding.item3Recycler,
            binding.item4Recycler,
            binding.item5Recycler
        )

        val buttons = arrayListOf(binding.button1, binding.button2, binding.button3)

        val tvTags = arrayListOf(
            binding.tv1Tag, binding.tv2Tag, binding.tv3Tag, binding.tv4Tag, binding.tv5Tag
        )

        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (!isChecked) {
                handleUncheckedState(buttons, checkedId)
            } else {
                handleCheckedState(recyclerViews, buttons, checkedId)
            }
        }

    }

    private fun handleUncheckedState(buttons: ArrayList<MaterialButton>, checkedId: Int) {
        buttons.forEach { button ->
            if (checkedId == button.id) {
                buttons.forEach { innerButton ->
                    innerButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
        }
    }

    private fun handleCheckedState(
        recyclerViews: ArrayList<RecyclerView>,
        buttons: ArrayList<MaterialButton>,
        checkedId: Int
    ) {

        when (checkedId) {
            binding.button1.id -> setupGameBoard(24, 3, 8, 5, recyclerViews)
            binding.button2.id -> setupGameBoard(28, 4, 7, 4, recyclerViews)
            binding.button3.id -> setupGameBoard(30, 5, 6, 6, recyclerViews)
        }

        updateButtonState(buttons, checkedId)
    }

    private fun updateButtonState(buttons: ArrayList<MaterialButton>, checkedId: Int) {
        buttons.forEachIndexed { index, button ->
            if (checkedId == button.id) {
                val visibility1 = if (index == 0) View.INVISIBLE else View.VISIBLE
                val visibility2 = if (index == 0 || index == 1) View.GONE else View.VISIBLE

                binding.tv4Tag.visibility = visibility1
                binding.item4Recycler.visibility = visibility1
                binding.tv5Tag.visibility = visibility2
                binding.item5Recycler.visibility = visibility2

                buttons.forEachIndexed { innerIndex, innerButton ->
                    val drawableResId = if (index == innerIndex) R.drawable.baseline_check_20 else 0
                    innerButton.setCompoundDrawablesWithIntrinsicBounds(
                        drawableResId,
                        0,
                        0,
                        0
                    )
                }

            }
        }
    }

    private fun setupGameBoard(
        totalCardCount: Int,
        recyclerViewCount: Int,
        cardsPerRow: Int,
        colCount: Int,
        recyclerViews: ArrayList<RecyclerView>
    ) {
        if (gameCardList.size < totalCardCount) {
            gameCardList.clear()
            gameCardList = cardRepository.getAllCards(false)
        }

        if (totalCardCount == 24) gameCardList.removeIf { card -> card.number == 12 }

        for (i in 0 until recyclerViewCount) {
            setupRecyclerView(recyclerViews[i], i != 0, cardsPerRow)
        }

        setupGridLayout(colCount)

        Log.d("ToggleBtn", "After: ${gameCardList.size}")
    }

    @SuppressLint("ResourceType")
    private fun setupGridLayout(colCount: Int) {
        val (marginDp, paddingLeft, paddingTop) = when (colCount) {
            5 -> Triple(30, 8, 60)
            4 -> Triple(54, 32, 60)
            else -> Triple(4, 4, 30)
        }

        binding.bottomLayout.removeAllViews()
        binding.bottomLayout.columnCount = colCount
        binding.bottomLayout.setPadding(paddingLeft, paddingTop, 0, 0)

        // GridLayout에 동적으로 아이템 추가
        for (card in gameCardList) {
            var cardView = LayoutInflater.from(this).inflate(R.layout.recycler_item, null)

            cardView.layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = marginDp
            }

            cardView.findViewById<ConstraintLayout>(R.id.card_sub_layout)
                .setBackgroundResource(R.drawable.logo)
            cardView.findViewById<CardView>(R.id.cv_card)
                .setBackgroundResource(R.drawable.linear_round)
            cardView.findViewById<TextView>(R.id.tv_emoji).visibility = View.GONE
            cardView.findViewById<TextView>(R.id.tv_topLeft).visibility = View.GONE
            cardView.findViewById<TextView>(R.id.tv_bottomRight).visibility = View.GONE

            // 카드 뷰를 GridLayout에 추가
            binding.bottomLayout.addView(cardView)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, isBack: Boolean, cardNum: Int) {
        var randomCards = mutableListOf<Card>()

        for (i in 0 until cardNum) randomCards.add(i, gameCardList.removeFirst())

        val adapter = CardAdapter(randomCards, isBack)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter


        cardAdapterList.add(adapter)
    }

}


