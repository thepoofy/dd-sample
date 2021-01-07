package com.thepoofy.sample.features.main_activity.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.thepoofy.sample.features.main_activity.databinding.MainActivityListItemBinding
import com.thepoofy.sample.lib.api.model.Restaurant
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class RestaurantListAdapter @Inject constructor(
    private val picasso: Picasso
) : ListAdapter<Restaurant, RestaurantListViewHolder>(RestaurantDiffCallback()) {

    private val scrollSubject = PublishSubject.create<Unit>()
    private val itemClickSubject = PublishSubject.create<Int>()

    fun update(list: List<Restaurant>) {
        submitList(list)
    }

    fun scrollEvents(): Observable<Unit> {
        return scrollSubject.hide()
    }

    fun itemClickEvents(): Observable<Int> {
        return itemClickSubject.hide()
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
        if (this.currentList.size == position + 1) {
            scrollSubject.onNext(Unit)
        }
    }

    override fun onViewAttachedToWindow(holder: RestaurantListViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener() {
            itemClickSubject.onNext(holder.restaurant.id)
        }
    }

    override fun onViewDetachedFromWindow(holder: RestaurantListViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}