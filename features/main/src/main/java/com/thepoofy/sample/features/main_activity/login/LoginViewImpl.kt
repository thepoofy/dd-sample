package com.thepoofy.sample.features.main_activity.login

import androidx.core.view.isVisible
import com.jakewharton.rxbinding4.view.clicks
import com.thepoofy.sample.features.main_activity.databinding.ContentLoginBinding
import com.thepoofy.sample.lib.api.form.LoginForm
import com.thepoofy.sample.lib.mvp.PresenterView
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoginViewImpl @Inject constructor() : PresenterView<ContentLoginBinding> {

    private lateinit var binding: ContentLoginBinding

    override fun onAttach(binding: ContentLoginBinding) {
        this.binding = binding
    }

    fun loginSubmit(): Observable<LoginForm> {
        return binding.submit.clicks()
            .map {
                LoginForm(binding.email.text.toString(), binding.password.text.toString())
            }
    }

    fun setIsVisible(isVisible: Boolean) {
        binding.root.isVisible = isVisible
    }
}