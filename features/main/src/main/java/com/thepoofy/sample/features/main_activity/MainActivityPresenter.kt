package com.thepoofy.sample.features.main_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.autoDispose
import com.thepoofy.sample.lib.core.SchedulersModule
import com.thepoofy.sample.lib.mvp.LifecyclePresenter
import timber.log.Timber
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val lifecycle: Lifecycle,
    private val schedulers: SchedulersModule.SchedulerProvider,
    private val scopeProvider: ScopeProvider,
    private val view: MainActivityView,
    private val repository: DataListRepository,
) : LifecyclePresenter() {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup) {
        super.onCreateView(layoutInflater, viewGroup)
        view.onAttach(layoutInflater, viewGroup)
        lifecycle.addObserver(this)
    }

    override fun onResume() {
        view.showLoading()
        repository.getData()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                view.hideLoading()
                if (it.isNotEmpty()) {
                    view.update(it)
                } else {
                    view.showEmptyList()
                }
            }, {
                view.hideLoading()
                Timber.w(it)
                view.showError()
            })

    }
}