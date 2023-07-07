package com.example.luckycardgame.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luckycardgame.R
import com.example.luckycardgame.databinding.RecyclerItemBinding
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.utils.UnicodeUtils


class CardAdapter(private val cardList: List<Card>, val isBack: Boolean) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    lateinit var binding: RecyclerItemBinding
    lateinit var itemList: MutableList<Card>
    private var context: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent

        itemList = cardList.toMutableList()

        return CardViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        // 아이템들이 겹치게 배치되게함
        holder.itemView.translationX = if (position == 0) 0f else -50f * position

        var card = cardList[position]
        card.clicked = isBack


        holder.bind(card)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    @SuppressLint("ResourceType")
    inner class CardViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rootCard.setOnClickListener {
                val card = cardList[adapterPosition]

                if (!card.clicked) {
                    binding.cardSubLayout.setBackgroundResource(R.drawable.logo)

                    binding.tvBottomRight.visibility = View.GONE
                    binding.tvTopLeft.visibility = View.GONE
                    binding.tvEmoji.visibility = View.GONE
                    card.clicked = true
                } else {
                    binding.cardSubLayout.setBackgroundResource(Color.TRANSPARENT)
                    binding.cvCard.setBackgroundResource(R.drawable.linear_round)
                    card.clicked = false

                    binding.tvBottomRight.visibility = View.VISIBLE
                    binding.tvTopLeft.visibility = View.VISIBLE
                    binding.tvEmoji.visibility = View.VISIBLE
                }
                animateCardFlip(binding.rootCard)
            }

        }



        fun bind(card: Card) {
            binding.tvBottomRight.text = card.number.toString()
            binding.tvTopLeft.text = card.number.toString()

            binding.tvEmoji.text = UnicodeUtils.convertToEmoji(card.type)

            if(card.clicked){
                binding.cardSubLayout.setBackgroundResource(R.drawable.logo)
                binding.cvCard.setBackgroundResource(R.drawable.linear_round)

                binding.tvBottomRight.visibility = View.GONE
                binding.tvTopLeft.visibility = View.GONE
                binding.tvEmoji.visibility = View.GONE
            }else{
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
            view.animate().withLayer()
                .rotationY(-90f)
                .setDuration(150)
                .withEndAction {
                    view.rotationY = -90f
                    view.animate().withLayer()
                        .rotationY(0f)
                        .setDuration(250)
                        .start()
                }.start()
        }
    }
}
