package com.thepoofy.sample.features.main_activity

import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.autoDispose
import com.thepoofy.sample.features.main_activity.databinding.ContentScrollingBinding
import com.thepoofy.sample.lib.core.SchedulersModule
import com.thepoofy.sample.lib.mvp.LifecyclePresenter
import timber.log.Timber
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val lifecycle: Lifecycle,
    private val locationProvider: LocationProvider,
    private val schedulers: SchedulersModule.SchedulerProvider,
    private val scopeProvider: ScopeProvider,
    private val view: MainActivityView,
    private val repository: DataListRepository,
) : LifecyclePresenter<ContentScrollingBinding>() {

    override fun onCreateView(binding: ContentScrollingBinding) {
        super.onCreateView(binding)
        view.onAttach(binding)
        lifecycle.addObserver(this)
    }

    override fun onResume() {
        view.showLoading()

        locationProvider.getCurrentLocation()
            .take(1)
            .flatMapSingle {
                repository.getRestaurants(
                    it.first,
                    it.second,
                    INITIAL_OFFSET,
                    OFFSET_INCREMENT
                )
            }
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

    companion object {
        const val INITIAL_OFFSET = 0
        const val OFFSET_INCREMENT = 50
    }
}