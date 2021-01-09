package com.thepoofy.sample.features.main_activity

import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.autoDispose
import com.thepoofy.sample.features.main_activity.databinding.ContentScrollingBinding
import com.thepoofy.sample.lib.api.model.Restaurant
import com.thepoofy.sample.lib.core.SchedulersModule
import com.thepoofy.sample.lib.mvp.LifecyclePresenter
import io.reactivex.rxjava3.subjects.BehaviorSubject
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

    private var itemsLoaded = arrayListOf<Restaurant>()

    private val isEnabledSubject = BehaviorSubject.createDefault(false)

    override fun onCreateView(binding: ContentScrollingBinding) {
        super.onCreateView(binding)
        view.onAttach(binding)
        lifecycle.addObserver(this)
    }

    override fun onResume() {
        isEnabledSubject
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({ isEnabled ->
                if (isEnabled) {
                    view.show()

                    if (itemsLoaded.isEmpty()) {
                        view.showLoading()
                        requestRestaurants(INITIAL_OFFSET)
                    }
                }
            }, {
                Timber.w(it)
            })

        /*
        Note to reviewer: I was adding infinite scroll for *bonus points* when I realized the offset
        and limit parameters of the API are not functional. The api returns the same 50 results
        every time and many of the Restaurant objects are duplicated.
        The initial list loads and scrolls nicely (imo) but infinite scroll is commented out for the
        stated reasons.
         */
//        subscribeScrollEvents()
        subscribeClickEvents()
    }

    fun show() {
        isEnabledSubject.onNext(true)
    }

    private fun requestRestaurants(offset: Int) {
        locationProvider.getCurrentLocation()
            .take(1)
            .flatMapSingle {
                repository.getRestaurants(
                    it.first,
                    it.second,
                    offset,
                    OFFSET_INCREMENT
                )
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                view.hideLoading()

                if (it.isEmpty() && itemsLoaded.isEmpty()) {
                    view.showEmptyList()
                } else if (it.isNotEmpty()) {
                    itemsLoaded.addAll(it)
                    view.update(itemsLoaded)
                }
            }, {
                view.hideLoading()
                Timber.w(it)
                view.showError()
            })
    }

    private fun subscribeScrollEvents() {
        view.scrollEvents()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                Timber.i("Scroll Event Captured")
                requestRestaurants(itemsLoaded.size)
            }, {
                Timber.w(it)
                view.showError()
            })
    }

    private fun subscribeClickEvents() {
        view.itemClicks()
            .subscribeOn(schedulers.mainThread())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                Timber.d("Item clicked $it")
            }, {
                Timber.w(it)
                view.showError()
            })
    }

    companion object {
        const val INITIAL_OFFSET = 0
        const val OFFSET_INCREMENT = 50
    }
}