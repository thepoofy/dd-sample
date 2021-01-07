package com.thepoofy.sample.features.main_activity

import androidx.lifecycle.Lifecycle
import autodispose2.lifecycle.TestLifecycleScopeProvider
import com.thepoofy.sample.lib.api.model.Restaurant
import com.thepoofy.sample.lib.core.SchedulersModule
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class MainActivityPresenterUnitTest {

    @Mock
    lateinit var lifecycle: Lifecycle

    @Mock
    lateinit var locationProvider: LocationProvider

    @Mock
    lateinit var repository: DataListRepository

    @Mock
    lateinit var view: MainActivityView

    private lateinit var presenter: MainActivityPresenter

    @Test
    fun onResume_usesLocationProvider() {
        // given
        val lat = 1.22345f
        val lng = 44.234f
        `when`(locationProvider.getCurrentLocation()).thenReturn(Observable.just(Pair(lat, lng)))
        val data: ArrayList<Restaurant> = arrayListOf(createData())
        `when`(repository.getRestaurants(eq(lat), eq(lng), anyInt(), anyInt()))
            .thenReturn(Single.just(data))

        // when
        presenter.onResume()

        // then
        verify(view).showLoading()
        verify(view).update(data)
        verify(view).hideLoading()

        verify(view, never()).showError()
        verify(view, never()).showEmptyList()
    }

    @Test
    fun onResume_loadsData() {
        // given
        whenLocationProvided()
        val data: ArrayList<Restaurant> = arrayListOf(createData())
        `when`(repository.getRestaurants(anyFloat(), anyFloat(), anyInt(), anyInt()))
            .thenReturn(Single.just(data))

        // when
        presenter.onResume()

        // then
        verify(view).showLoading()
        verify(view).update(data)
        verify(view).hideLoading()

        verify(view, never()).showError()
        verify(view, never()).showEmptyList()
    }

    @Test
    fun onResume_whenError_showsErrorMessage() {
        // given
        whenLocationProvided()
        `when`(repository.getRestaurants(anyFloat(), anyFloat(), anyInt(), anyInt()))
            .thenReturn(Single.error(RuntimeException()))

        // when
        presenter.onResume()

        // then
        verify(view).showLoading()
        verify(view).showError()
        verify(view, never()).update(anyListOf(Restaurant::class.java))
    }

    @Test
    fun onResume_whenEmpty_showsEmptyMessage() {
        // given
        whenLocationProvided()
        `when`(repository.getRestaurants(anyFloat(), anyFloat(), anyInt(), anyInt()))
            .thenReturn(Single.just(emptyList()))

        // when
        presenter.onResume()

        // then
        verify(view).showLoading()
        verify(view).showEmptyList()
        verify(view, never()).showError()
        verify(view, never()).update(anyListOf(Restaurant::class.java))
    }

    @Before
    fun setup() {
        val scopeProvider = TestLifecycleScopeProvider.create()
        presenter = MainActivityPresenter(
            lifecycle,
            locationProvider,
            TestSchedulers(),
            scopeProvider,
            view,
            repository
        )

        scopeProvider.start()
    }

    private class TestSchedulers : SchedulersModule.SchedulerProvider {
        override fun io(): Scheduler {
            return Schedulers.trampoline()
        }

        override fun mainThread(): Scheduler {
            return Schedulers.trampoline()
        }
    }

    private fun createData(): Restaurant {
        return Restaurant("image.png", 99, "Burgers", 1234, "GoodBurger", "Closed")
    }

    private fun whenLocationProvided() {
        `when`(locationProvider.getCurrentLocation()).thenReturn(Observable.just(Pair(1.0f, 1.0f)))
    }
}