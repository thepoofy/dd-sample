package com.thepoofy.sample.features.main_activity.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.thepoofy.sample.features.main_activity.databinding.MainActivityListItemBinding
import com.thepoofy.sample.lib.api.model.Restaurant
import javax.inject.Inject


class RestaurantListAdapter @Inject constructor(
    private val picasso: Picasso
) : ListAdapter<Restaurant, RestaurantListViewHolder>(RestaurantDiffCallback()) {

    fun update(list: List<Restaurant>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListViewHolder {
        return RestaurantListViewHolder(
            MainActivityListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantListViewHolder, position: Int) {
        holder.bindViewHolder(getItem(position), picasso)
    }
}