package com.thepoofy.sample.features.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thepoofy.sample.lib.core.CoreComponentProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))

        DaggerMainActivityComponent.builder()
            .coreComponent((application as? CoreComponentProvider)?.provideCoreComponent())
            .module(MainActivityComponent.Module(this))
            .build()
            .inject(this)

        presenter.onCreateView(layoutInflater, findViewById(R.id.activity_scrolling_root))
    }

}