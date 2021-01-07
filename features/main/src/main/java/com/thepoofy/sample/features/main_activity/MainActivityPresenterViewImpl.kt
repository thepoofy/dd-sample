package com.thepoofy.sample.features.main_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.thepoofy.sample.features.main_activity.list.StringListAdapter
import javax.inject.Inject

class MainActivityPresenterViewImpl @Inject constructor(
    private val stringListAdapter: StringListAdapter
) : MainActivityView {

    private lateinit var container: ViewGroup
    private lateinit var loadingView: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var snackbar: Snackbar
    private lateinit var emptyGroup: ViewGroup

    override fun onAttach(layoutInflater: LayoutInflater, viewGroup: ViewGroup) {
        container = viewGroup.findViewById(R.id.scrolling_container)
        viewGroup.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
            viewGroup.resources.getString(R.string.app_name)

        recyclerView = viewGroup.findViewById(R.id.main_activity_list_recyclerview)
        recyclerView.adapter = stringListAdapter

        loadingView = viewGroup.findViewById(R.id.main_activity_list_loading)
        snackbar = Snackbar.make(
            viewGroup,
            R.string.main_activity_list_error_label,
            Snackbar.LENGTH_LONG
        )

        emptyGroup = viewGroup.findViewById(R.id.main_activity_list_empty_group)
    }

    override fun showLoading() {
        container.isVisible = true

        recyclerView.isVisible = false
        emptyGroup.isVisible = false

        loadingView.isVisible = true
    }

    override fun hideLoading() {
        loadingView.isVisible = false
    }

    override fun update(data: List<String>) {
        container.isVisible = true
        emptyGroup.isVisible = false

        recyclerView.isVisible = true
        stringListAdapter.update(data)
    }

    override fun showEmptyList() {
        recyclerView.isVisible = false

        emptyGroup.isVisible = true
    }

    override fun showError() {
        snackbar.show()
    }

    override fun hide() {
        container.isVisible = false
    }
}