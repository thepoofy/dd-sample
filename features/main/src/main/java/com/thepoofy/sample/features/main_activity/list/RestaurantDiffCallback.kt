package com.thepoofy.sample.features.main_activity.list

import androidx.recyclerview.widget.DiffUtil
import com.thepoofy.sample.lib.api.model.Restaurant

class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}