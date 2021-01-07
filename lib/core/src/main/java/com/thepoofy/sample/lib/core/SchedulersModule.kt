package com.thepoofy.sample.lib.core

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
class SchedulersModule {

    @Provides
    fun schedulers(): SchedulerProvider = SchedulerProviderImpl()

    interface SchedulerProvider {
        fun io(): Scheduler
        fun mainThread(): Scheduler
    }

    class SchedulerProviderImpl : SchedulerProvider {
        override fun io(): Scheduler {
            return Schedulers.io()
        }

        override fun mainThread(): Scheduler {
            return AndroidSchedulers.mainThread()
        }
    }
}