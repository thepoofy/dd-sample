package com.thepoofy.sample.features.main_activity.login

import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.autoDispose
import com.thepoofy.sample.features.main_activity.databinding.ContentLoginBinding
import com.thepoofy.sample.lib.api.DoorDashApi
import com.thepoofy.sample.lib.api.form.LoginForm
import com.thepoofy.sample.lib.core.SchedulersModule
import com.thepoofy.sample.lib.mvp.LifecyclePresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val api: DoorDashApi,
    private val lifecycle: Lifecycle,
    private val view: LoginViewImpl,
    private val schedulers: SchedulersModule.SchedulerProvider,
    private val scopeProvider: ScopeProvider,
    private val sharedPreferences: SharedPreferences,

    ) : LifecyclePresenter<ContentLoginBinding>() {

    private val loginCompleteSubject = PublishSubject.create<Unit>()

    override fun onCreateView(binding: ContentLoginBinding) {
        super.onCreateView(binding)

        view.onAttach(binding)
        lifecycle.addObserver(this)
    }

    override fun onResume() {
        super.onResume()

        view.loginSubmit()
            .subscribeOn(schedulers.mainThread())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                requestLogin(it)
            }, {
                Timber.w(it)
            })
    }

    private fun requestLogin(loginForm: LoginForm) {
        api.login(loginForm)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                sharedPreferences.edit().putString("token", it.token).apply()
                loginCompleteSubject.onNext(Unit)
            }, {
                Timber.w(it)
            })
    }

    fun show() {
        view.setIsVisible(true)
    }

    fun hide() {
        view.setIsVisible(false)
    }

    fun loginCompleteEvents(): Observable<Unit> {
        return loginCompleteSubject.hide()
    }
}