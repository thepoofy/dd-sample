package com.thepoofy.sample.features.main_activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.squareup.moshi.Moshi
import com.thepoofy.sample.features.main_activity.list.StringListAdapter
import com.thepoofy.sample.features.main_activity.storage.FileStorage
import com.thepoofy.sample.lib.api.ApiModule
import com.thepoofy.sample.lib.api.SampleApi
import com.thepoofy.sample.lib.core.CoreComponent
import dagger.Component
import dagger.Provides

@MainActivityScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [ApiModule::class, MainActivityComponent.Module::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @dagger.Module
    class Module(private val activity: AppCompatActivity) {

        @Provides
        @MainActivityScope
        fun layoutInflater(): LayoutInflater = activity.layoutInflater

        @Provides
        @MainActivityScope
        fun lifecycle(): Lifecycle = activity.lifecycle

        @Provides
        @MainActivityScope
        fun scopeProvider(lifecycle: Lifecycle): ScopeProvider =
            AndroidLifecycleScopeProvider.from(lifecycle)

        @Provides
        @MainActivityScope
        fun fileStorage(moshi: Moshi) = FileStorage(activity, moshi)

        @Provides
        @MainActivityScope
        fun repository(api: SampleApi, fileStorage: FileStorage): DataListRepository =
            DataListRepositoryImpl(api, fileStorage)

        @Provides
        @MainActivityScope
        fun view(adapter: StringListAdapter): MainActivityView =
            MainActivityPresenterViewImpl(adapter)
    }
}