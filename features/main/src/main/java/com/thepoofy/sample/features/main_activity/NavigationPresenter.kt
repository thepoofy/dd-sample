package com.thepoofy.sample.features.main_activity

import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.autoDispose
import com.thepoofy.sample.features.main_activity.databinding.ActivityScrollingBinding
import com.thepoofy.sample.features.main_activity.login.LoginPresenter
import com.thepoofy.sample.lib.core.SchedulersModule
import com.thepoofy.sample.lib.mvp.LifecyclePresenter
import timber.log.Timber
import javax.inject.Inject

class NavigationPresenter @Inject constructor(
    private val lifecycle: Lifecycle,
    private val schedulers: SchedulersModule.SchedulerProvider,
    private val scopeProvider: ScopeProvider,
    private val mainActivityPresenter: MainActivityPresenter,
    private val loginPresenter: LoginPresenter,
    private val sharedPreferences: SharedPreferences,
    ) : LifecyclePresenter<ActivityScrollingBinding>() {

    override fun onCreateView(binding: ActivityScrollingBinding) {
        super.onCreateView(binding)

        mainActivityPresenter.onCreateView(binding.scrollingContent)
        loginPresenter.onCreateView(binding.loginContent)

        lifecycle.addObserver(this)
    }

    override fun onResume() {
        super.onResume()

        val token: String? = sharedPreferences.getString("token", null)

        if (token == null) {
            // show login
            loginPresenter.show()
        } else {
            // show main
            mainActivityPresenter.show()
        }

        loginPresenter.loginCompleteEvents()
            .subscribeOn(schedulers.mainThread())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                mainActivityPresenter.show()
                loginPresenter.hide()
            }, {
                Timber.w(it)
            })
    }
}