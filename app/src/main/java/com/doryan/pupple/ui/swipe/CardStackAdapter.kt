package com.doryan.pupple.ui.swipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doryan.pupple.databinding.CardStackBinding
import com.doryan.pupple.model.SwipeDog

private object DiffCallback : DiffUtil.ItemCallback<SwipeDog>() {
    override fun areItemsTheSame(oldItem: SwipeDog, newItem: SwipeDog): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SwipeDog, newItem: SwipeDog): Boolean {
        return oldItem == newItem
    }
}

class CardStackAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewmodel: SwipeViewModel
) : ListAdapter<SwipeDog, CardStackAdapter.CardStackViewHolder>(DiffCallback) {

    class CardStackViewHolder(
        private val binding: CardStackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: SwipeDog,
            viewLifecycleOwner: LifecycleOwner,
            viewmodel: SwipeViewModel
        ) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                dog = item
                swipeViewModel = viewmodel
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CardStackViewHolder(CardStackBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CardStackViewHolder, position: Int) {
        holder.bind(getItem(position), viewLifecycleOwner, viewmodel)
    }
}