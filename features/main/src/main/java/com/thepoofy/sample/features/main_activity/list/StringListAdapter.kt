package com.thepoofy.sample.features.main_activity.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.thepoofy.sample.features.main_activity.R
import javax.inject.Inject


class StringListAdapter @Inject constructor(
    private val picasso: Picasso
) : ListAdapter<String, StringListViewHolder>(StringDiffCallback()) {

    fun update(list: List<String>) {
        submitList(list.sortedBy { selector -> selector })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringListViewHolder {
        return StringListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_activity_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StringListViewHolder, position: Int) {
        holder.bindViewHolder(getItem(position), picasso)
    }
}