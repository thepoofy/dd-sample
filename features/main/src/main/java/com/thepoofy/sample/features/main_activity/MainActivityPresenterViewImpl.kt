package com.thepoofy.sample.features.main_activity

import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.thepoofy.sample.features.main_activity.databinding.ContentScrollingBinding
import com.thepoofy.sample.features.main_activity.list.RestaurantListAdapter
import com.thepoofy.sample.lib.api.model.Restaurant
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MainActivityPresenterViewImpl @Inject constructor(
    private val restaurantListAdapter: RestaurantListAdapter
) : MainActivityView {

    private lateinit var binding: ContentScrollingBinding
    private lateinit var snackbar: Snackbar

    override fun onAttach(binding: ContentScrollingBinding) {
        this.binding = binding
        initView()
    }

    private fun initView() {
        binding.recyclerview.adapter = restaurantListAdapter

        snackbar = Snackbar.make(
            binding.root,
            R.string.main_activity_list_error_label,
            Snackbar.LENGTH_LONG
        )
    }

    override fun showLoading() {
        setIsVisible(true)
        binding.recyclerview.isVisible = false
        binding.emptyGroup.isVisible = false

        binding.loading.isVisible = true
    }

    override fun hideLoading() {
        binding.loading.isVisible = false
    }

    override fun update(data: List<Restaurant>) {
        setIsVisible(true)
        setIsListVisible(true)
        restaurantListAdapter.update(data)
    }

    override fun showEmptyList() {
        setIsVisible(true)
        setIsListVisible(false)
    }

    override fun showError() {
        snackbar.show()
    }

    override fun hide() {
        setIsVisible(false)
    }

    override fun itemClicks(): Observable<Int> {
        return restaurantListAdapter.itemClickEvents()
    }

    override fun scrollEvents(): Observable<Unit> {
        return restaurantListAdapter.scrollEvents()
    }

    private fun setIsListVisible(isVisible: Boolean) {
        binding.recyclerview.isVisible = isVisible
        binding.emptyGroup.isVisible = !isVisible
    }

    private fun setIsVisible(isVisible: Boolean) {
        binding.root.isVisible = isVisible
    }
}