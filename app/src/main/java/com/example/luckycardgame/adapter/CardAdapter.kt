package com.example.luckycardgame.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.luckycardgame.R
import com.example.luckycardgame.databinding.RecyclerItemBinding
import com.example.luckycardgame.listener.OnCardClickListener
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.utils.UnicodeUtils

/**
 * CardAdapter 클래스는 RecyclerView의 어댑터로서, 카드 목록을 화면에 표시합니다.
 * 카드는 앞면과 뒷면을 가지며, 뒤집는 애니메이션을 지원합니다.
 */
class CardAdapter(private val cardList: List<Card>, val isBack: Boolean, val onCardClickListener: OnCardClickListener) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var itemList: MutableList<Card> = cardList.toMutableList()
    private var parentContext: Context? = null
    var isClickable = true
    var leftMostCardIndex = 0
    var rightMostCardIndex = cardList.size - 1

    /**
     * ViewHolder가 생성될 때 호출되는 메소드입니다. 카드의 레이아웃을 초기화합니다.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        parentContext = parent.context
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        itemList = cardList.toMutableList()

        return CardViewHolder(binding)
    }


    /**
     * ViewHolder에 데이터를 바인딩할 때 호출되는 메소드입니다. 각 카드의 위치에 따라 겹치게 배치됩니다.
     */
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var card = cardList[position]
        card.clicked = isBack
        holder.bind(card)
    }

    /**
     * 목록에 표시될 항목의 총 개수를 반환하는 메소드입니다.
     */
    override fun getItemCount(): Int {
        return cardList.size
    }

    /**
     * CardViewHolder 클래스는 각 카드를 표시하는 뷰 홀더입니다.
     * 카드를 클릭하면 카드가 뒤집어지는 애니메이션을 수행합니다.
     */
    @SuppressLint("ResourceType")
    inner class CardViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rootCard.setOnClickListener {
                if (isClickable) {
                    val position = adapterPosition
                    onCardClickListener.onCardClick(adapterPosition, this)

                    // 현재 카드가 뒤집을 수 있는 가장 왼쪽 혹은 오른쪽 카드인지 확인
                    if (position == leftMostCardIndex || position == rightMostCardIndex) {
                        val card = cardList[position]

                        if (card.clicked) {     // 카드 뒷면일시
                            binding.cardSubLayout.setBackgroundResource(Color.TRANSPARENT)
                            binding.cvCard.setBackgroundResource(R.drawable.linear_round)
                            card.clicked = false

                            binding.tvBottomRight.visibility = View.VISIBLE
                            binding.tvTopLeft.visibility = View.VISIBLE
                            binding.tvEmoji.visibility = View.VISIBLE

                            if (position == leftMostCardIndex) {
                                leftMostCardIndex++
                            } else if (position == rightMostCardIndex) {
                                rightMostCardIndex--
                            }

                        }
                        // 카드 앞면 일시 TODO
                        else {
                        }

                        animateCardFlip(binding.rootCard)
                    }
                } else {   // 턴이 아닌 사용자가 카드를 선택할 경우
                    Toast.makeText(parentContext!!, "사용자의 턴이 아닙니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /**
         * 각 카드에 데이터를 바인딩하는 메소드입니다.
         * 카드의 숫자와 이모지, 카드의 뒷면 또는 앞면을 표시하는 상태를 설정합니다.
         */

        fun bind(card: Card) {
            binding.tvBottomRight.text = card.number.toString()
            binding.tvTopLeft.text = card.number.toString()

            binding.tvEmoji.text = UnicodeUtils.convertToEmoji(card.type)

            if (card.clicked) {
                binding.cardSubLayout.setBackgroundResource(R.drawable.logo)
                binding.cvCard.setBackgroundResource(R.drawable.linear_round)

                binding.tvBottomRight.visibility = View.GONE
                binding.tvTopLeft.visibility = View.GONE
                binding.tvEmoji.visibility = View.GONE
            } else {
                binding.cardSubLayout.setBackgroundResource(Color.TRANSPARENT)
                binding.cvCard.setBackgroundResource(R.drawable.linear_round)

                binding.tvBottomRight.visibility = View.VISIBLE
                binding.tvTopLeft.visibility = View.VISIBLE
                binding.tvEmoji.visibility = View.VISIBLE
            }

        }

        /**
         * 애니메이션 효과를 적용하여 카드를 뒤집는 기능을 수행하는 함수입니다.
         * 뷰의 카메라 거리 및 회전을 조정하여 카드 뒷면을 보여주는 애니메이션 효과를 적용하고,
         * 일정 시간 후에 카드를 다시 앞면으로 돌려주는 애니메이션 효과를 적용합니다.
         *
         * @param view 뒤집을 카드 뷰
         */
        private fun animateCardFlip(view: View) {
            val scale: Float = view.resources.displayMetrics.density
            val distance: Float = view.cameraDistance * (scale + scale / 3)

            view.cameraDistance = distance
            view.animate().withLayer().rotationY(-90f).setDuration(150).withEndAction {
                view.rotationY = -90f
                view.animate().withLayer().rotationY(0f).setDuration(250).start()
            }.start()
        }
    }
}
