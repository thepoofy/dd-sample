package com.thepoofy.sample.features.main_activity

import com.thepoofy.sample.features.main_activity.databinding.ContentScrollingBinding
import com.thepoofy.sample.lib.api.model.Restaurant
import com.thepoofy.sample.lib.mvp.LoadingView
import com.thepoofy.sample.lib.mvp.PresenterView
import io.reactivex.rxjava3.core.Observable

interface MainActivityView : LoadingView, PresenterView<ContentScrollingBinding> {

    fun update(data: List<Restaurant>)

    fun showEmptyList()

    fun showError()

    fun hide()

    fun scrollEvents(): Observable<Unit>

    fun itemClicks(): Observable<Int>
}