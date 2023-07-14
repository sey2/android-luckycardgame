package com.example.luckycardgame.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luckycardgame.R
import com.example.luckycardgame.databinding.RecyclerItemBinding
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.utils.UnicodeUtils

class CardAdapter(private val cardList: List<Card>, private val isBack: Boolean) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var itemList: MutableList<Card> = cardList.toMutableList()
    private var parentContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        parentContext = parent.context
        itemList = cardList.toMutableList()

        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.itemView.translationX = if (position == 0) 0f else -50f * position

        var card = cardList[position]
        card.clicked = isBack
        holder.bind(card)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    @SuppressLint("ResourceType")
    inner class CardViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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