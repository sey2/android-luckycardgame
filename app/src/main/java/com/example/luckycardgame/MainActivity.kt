package com.example.luckycardgame

import android.os.Bundle
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
import com.example.luckycardgame.adapter.ItemOffsetDecoration
import com.example.luckycardgame.databinding.ActivityMainBinding
import com.example.luckycardgame.listener.OnCardClickListener
import com.example.luckycardgame.model.LuckyGame
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity(), OnCardClickListener {

    private var game: LuckyGame = LuckyGame(5)
    private lateinit var binding: ActivityMainBinding
    private var recyclerViews: ArrayList<RecyclerView> = arrayListOf()
    private var buttons: ArrayList<MaterialButton> = arrayListOf()
    private var tvTags: ArrayList<TextView> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootLayout)

        recyclerViews = arrayListOf(binding.item1Recycler,
            binding.item2Recycler, binding.item3Recycler,
            binding.item4Recycler, binding.item5Recycler)

        val itemDecoration = ItemOffsetDecoration(-50)
        recyclerViews.forEach {it.addItemDecoration(itemDecoration)}

        buttons = arrayListOf(binding.button1, binding.button2, binding.button3)
        tvTags = arrayListOf(binding.tv1Tag, binding.tv2Tag, binding.tv3Tag, binding.tv4Tag, binding.tv5Tag)

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) {
                hideViews(recyclerViews)
                resetButtonsDrawable(buttons, checkedId)
                clearBottomLayout()
            } else {
                showViews(recyclerViews)
                handleCheckedState(buttons, checkedId)
                setTurnRecyclerViews()
            }
        }

        binding.btnRestart.setOnClickListener{
            setupGameBoard(game.getParticipants().size, binding.bottomLayout.columnCount)
            tvTags.forEach { it.setBackgroundResource(R.drawable.item_linear_round)}
            binding.tvResult.visibility = View.GONE
            binding.tvWinner.visibility = View.GONE
            binding.btnRestart.visibility = View.GONE
            binding.bottomLayout.visibility = View.VISIBLE
            binding.toggleButton.visibility = View.VISIBLE
        }
    }

    private fun clearBottomLayout() {
        recyclerViews.forEach { it.adapter = null }
    }


    private fun hideViews(views: List<View>) {
        views.forEach { it.visibility = View.GONE }
    }

    private fun showViews(views: List<View>) {
        views.forEach { it.visibility = View.VISIBLE }
    }
    private fun resetButtonsDrawable(buttons: ArrayList<MaterialButton>, checkedId: Int) {
        buttons.forEach { button ->
            if (checkedId == button.id) {
                buttons.forEach { it.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0) }
            }
        }
    }

    private fun handleCheckedState(
        buttons: ArrayList<MaterialButton>,
        checkedId: Int
    ) {

        when (checkedId) {
            binding.button1.id ->  setupGameBoard( 3, 5)
            binding.button2.id ->  setupGameBoard(4, 4)
            binding.button3.id ->  setupGameBoard(5, 6)
        }

        setTurnRecyclerViews()
        buttons.forEach { button -> updateButtonState(button, checkedId) }
    }
    private fun updateButtonState(button: MaterialButton, checkedId: Int) {
        if (checkedId == button.id) {
            val visibility1 = if (button == buttons[0]) View.INVISIBLE else View.VISIBLE
            val visibility2 = if (button == buttons[0] || button == buttons[1]) View.GONE else View.VISIBLE

            binding.tv4Tag.visibility = visibility1
            binding.item4Recycler.visibility = visibility1
            binding.tv5Tag.visibility = visibility2
            binding.item5Recycler.visibility = visibility2

            buttons.forEach { innerButton ->
                val drawableResId = if (button == innerButton) R.drawable.baseline_check_20 else 0
                innerButton.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0)
            }
        }

    }

    private fun setupGameBoard(
        recyclerViewCount: Int,
        colCount: Int,
    ) {
        game = LuckyGame(recyclerViewCount)
        makeRecycler()
        setupGridLayout(colCount)
    }
    private fun setupGridLayout(colCount: Int) {
        val (marginDp, paddingLeft, paddingTop) = when (colCount) {
            5 -> Triple(30, 8, 120)
            4 -> Triple(54, 32, 120)
            else -> Triple(4, 4, 70)
        }

        tvTags.forEach { it.text = "" }

        binding.bottomLayout.removeAllViews()
        binding.bottomLayout.columnCount = colCount
        binding.bottomLayout.setPadding(paddingLeft, paddingTop, 0, 0)

        // GridLayout에 동적으로 아이템 추가
        for (card in game.getFloorCards()) {
            val cardView = LayoutInflater.from(this).inflate(R.layout.recycler_item, null)

            cardView.layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
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

    private fun setTurnRecyclerViews(){
        tvTags[0].setBackgroundResource(R.drawable.item_linear_round_turn)
        recyclerViews.forEach { (it.adapter as? CardAdapter)?.isClickable = false }

        (recyclerViews[0].adapter as? CardAdapter)?.isClickable = true
    }

    private fun makeRecycler(){
        for (i in 1..game.getParticipants().size) {
            val cardList =  game.getParticipantCards("Participant ${i - 1}")

            val adapter = CardAdapter(cardList!!, true, this)

            recyclerViews[i-1].layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            recyclerViews[i-1].adapter = adapter

        }
    }

    private fun turnChange(){
        game.turnRest()
        makeRecycler()
        setTurnRecyclerViews()
        game.turn = 1
    }

    private fun showResultView(combinedResults: List<String>){
        if(combinedResults.isNotEmpty()){
            binding.tvResult.visibility = View.VISIBLE
            binding.tvWinner.visibility = View.VISIBLE

            tvTags[0].setBackgroundResource(R.drawable.item_linear_round)
            recyclerViews.forEach { it.adapter = null }

            combinedResults.forEach{ id ->
                val cardList =  game.getParticipant(id).collectionSet
                val adapter = CardAdapter(cardList!!, false, this)
                val index = id.split(" ")[1].toInt()

                recyclerViews[index].adapter = adapter
                tvTags[index].setBackgroundResource(R.drawable.item_linear_round_turn)
            }

            val winnerList = combinedResults.map{ it.split(" ") [1].toInt() + 'A'.code}
            var text = winnerList.sorted().joinToString(separator = ", ") { it.toChar().toString() }

            binding.tvWinner.text = "이번 게임은 " + text + "사용자가 승리했습니다"
            binding.btnRestart.visibility = View.VISIBLE
            binding.bottomLayout.visibility = View.GONE
            binding.toggleButton.visibility = View.INVISIBLE
        }
    }

    override fun onCardClick(cardIndex: Int, holder: CardAdapter.CardViewHolder) {
        // 카드를 선택할 수 있는 경우, 카드 상태 업데이트 및 게임 상태 업데이트
        if (game.canSelected(game.currentPlayerId, cardIndex)) {
            val index = game.isNextTurn()

            // 참가자가 선택한 카드가 3장이거나 선택할 수 있는 카드를 전부 선택 했을 때 다음 참가자의 턴으로 넘김
            if (index >= 0) {
                var nextIdx = if (index == game.getParticipants().size - 1) 0 else index + 1

                game.currentPlayerId = "Participant $nextIdx"

                (recyclerViews[nextIdx].adapter as? CardAdapter)?.isClickable = true
                (recyclerViews[index].adapter as? CardAdapter)?.isClickable = false

                tvTags[index].setBackgroundResource(R.drawable.item_linear_round)
                tvTags[nextIdx].setBackgroundResource(R.drawable.item_linear_round_turn)

                game.turn++

                // 모든 참가자들이 턴을 돌았을 때
                if(game.turn > game.getParticipants().size){
                    turnChange()

                    // 턴이 끝났을시 우승자를 판별하는 조건이 3가지 이므로
                    val winnerResult1 = game.service.findSumAndSubResultSeven().let { if (it == "none") listOf() else it.split("@") }
                    val winnerResult2 = game.service.findParticipantWithSeven().let { if (it == "none") listOf() else it.split("@") }
                    val winnerResult3 = game.service.findParticipantsWithNoCards().let { if (it == "none") listOf() else it.split("@") }


                    // 우승 조건 여러개를 부합하는 참가자가 있을 수 있으므로
                    val combinedResults = (winnerResult1 + winnerResult2 + winnerResult3).distinct()

                    if(combinedResults.isNotEmpty()){
                        showResultView(combinedResults)
                    }

                }
            }
        }
    }
}


