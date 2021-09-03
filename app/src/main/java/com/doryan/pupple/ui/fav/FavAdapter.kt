package com.doryan.pupple.ui.fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doryan.pupple.model.FavDog
import com.doryan.pupple.databinding.ListFavDogsBinding

private object DiffCallback : DiffUtil.ItemCallback<FavDog>() {
    override fun areItemsTheSame(oldItem: FavDog, newItem: FavDog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavDog, newItem: FavDog): Boolean {
        return oldItem == newItem
    }
}

class FavAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: FavViewModel
) : ListAdapter<FavDog, FavAdapter.FavViewHolder>(DiffCallback) {

    class FavViewHolder(
        private val binding: ListFavDogsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: FavDog,
            viewLifecycleOwner: LifecycleOwner,
            viewModel: FavViewModel
        ) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                favDog = item
                favViewModel = viewModel
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavViewHolder(ListFavDogsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(getItem(position), viewLifecycleOwner, viewModel)
    }

}