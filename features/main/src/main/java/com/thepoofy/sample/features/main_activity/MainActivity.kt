package com.thepoofy.sample.features.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thepoofy.sample.features.main_activity.databinding.ActivityScrollingBinding
import com.thepoofy.sample.lib.core.CoreComponentProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DaggerMainActivityComponent.factory()
            .create((application as? CoreComponentProvider)?.provideCoreComponent()!!, this)
            .inject(this)

        presenter.onCreateView(binding.scrollingContent)
    }

}