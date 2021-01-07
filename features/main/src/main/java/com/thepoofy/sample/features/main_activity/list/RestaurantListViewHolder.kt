package com.thepoofy.sample.features.main_activity.list

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thepoofy.sample.features.main_activity.databinding.MainActivityListItemBinding
import com.thepoofy.sample.lib.api.model.Restaurant

class RestaurantListViewHolder(private val binding: MainActivityListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var restaurant: Restaurant

    fun bindViewHolder(restaurant: Restaurant, picasso: Picasso) {
        this.restaurant = restaurant
        binding.name.text = restaurant.name
        binding.description.text = restaurant.description
        binding.status.text = restaurant.status

        picasso.load(restaurant.coverImgUrl)
            .into(binding.image)
    }
}