package com.thepoofy.sample.features.main_activity

import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import autodispose2.ScopeProvider
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.thepoofy.sample.features.main_activity.list.RestaurantListAdapter
import com.thepoofy.sample.lib.api.ApiModule
import com.thepoofy.sample.lib.api.DoorDashApi
import com.thepoofy.sample.lib.core.CoreComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import io.reactivex.rxjava3.core.Observable

@MainActivityScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [ApiModule::class, MainActivityComponent.Module::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface MainActivityComponentFactory {
        fun create(
            core: CoreComponent,
            @BindsInstance activity: MainActivity
        ): MainActivityComponent
    }

    @dagger.Module
    class Module {

        @Provides
        @MainActivityScope
        fun layoutInflater(activity: MainActivity): LayoutInflater = activity.layoutInflater

        @Provides
        @MainActivityScope
        fun lifecycle(activity: MainActivity): Lifecycle = activity.lifecycle

        @Provides
        @MainActivityScope
        fun scopeProvider(lifecycle: Lifecycle): ScopeProvider =
            AndroidLifecycleScopeProvider.from(lifecycle)

        @Provides
        @MainActivityScope
        fun repository(api: DoorDashApi): DataListRepository = DataListRepositoryImpl(api)

        @Provides
        @MainActivityScope
        fun view(adapter: RestaurantListAdapter): MainActivityView =
            MainActivityPresenterViewImpl(adapter)

        @Provides
        @MainActivityScope
        fun locationProvider(): LocationProvider = object : LocationProvider {
            override fun getCurrentLocation(): Observable<Pair<Float, Float>> {
                return Observable.just(Pair(37.422740f, -122.139956f))
            }
        }
    }
}
