package com.thepoofy.sample.features.main_activity.list

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import com.thepoofy.sample.features.main_activity.R

class StringListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val label = v.findViewById<MaterialTextView>(R.id.label)
    private val image = v.findViewById<AppCompatImageView>(R.id.image)

    lateinit var data: String

    fun bindViewHolder(str: String, picasso: Picasso) {
        this.data = str
        this.label.text = data
//        picasso.load(employee.photoUrlSmall)
//            .error(R.drawable.ic_face_48dp)
//            .into(image)
    }
}