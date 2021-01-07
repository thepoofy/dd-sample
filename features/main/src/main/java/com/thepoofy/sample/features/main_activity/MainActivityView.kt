package com.thepoofy.sample.features.main_activity

import com.thepoofy.sample.lib.mvp.LoadingView
import com.thepoofy.sample.lib.mvp.PresenterView

interface MainActivityView : LoadingView, PresenterView {

    fun update(data: List<String>)

    fun showEmptyList()

    fun showError()

    fun hide()
}