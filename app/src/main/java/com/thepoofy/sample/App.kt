package com.thepoofy.sample

import android.app.Application
import com.thepoofy.sample.lib.core.CoreComponent
import com.thepoofy.sample.lib.core.CoreComponentProvider
import com.thepoofy.sample.lib.core.DaggerCoreComponent
import timber.log.Timber

class App : Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()

        coreComponent = DaggerCoreComponent.factory().create(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Add crash reporting tree here, with crash reporting itself
//      Timber.plant(CrashReportingTree())
        }
    }

    override fun provideCoreComponent() = coreComponent
}