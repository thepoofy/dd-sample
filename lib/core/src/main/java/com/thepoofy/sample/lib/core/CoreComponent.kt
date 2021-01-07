package com.thepoofy.sample.lib.core

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MoshiModule::class, PicassoModule::class, SchedulersModule::class]
)
interface CoreComponent {

    fun picasso(): Picasso
    fun schedulers(): SchedulersModule.SchedulerProvider
    fun moshi(): Moshi

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }
}