package com.thepoofy.sample.features.main_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import autodispose2.lifecycle.TestLifecycleScopeProvider
import com.thepoofy.sample.lib.core.SchedulersModule
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
    lateinit var repository: DataListRepository

    @Mock
    lateinit var view: MainActivityView

    private lateinit var presenter: MainActivityPresenter

    @Test
    fun onCreateView_attachesCorrectly() {
        // given
        val layoutInflater = mock(LayoutInflater::class.java)
        val viewGroup = mock(ViewGroup::class.java)
        verifyZeroInteractions(view, lifecycle)

        // when
        presenter.onCreateView(layoutInflater, viewGroup)

        // then
        verify(view).onAttach(layoutInflater, viewGroup)
        verify(lifecycle).addObserver(presenter)
    }

    @Test
    fun onResume_loadsData() {
        // given
        val data = arrayListOf(createData())
        `when`(repository.getData()).thenReturn(Single.just(data))

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
        `when`(repository.getData()).thenReturn(Single.error(RuntimeException()))

        // when
        presenter.onResume()

        // then
        verify(view).showLoading()
        verify(view).showError()
        verify(view, never()).update(anyListOf(String::class.java))
    }

    @Test
    fun onResume_whenEmpty_showsEmptyMessage() {
        // given
        `when`(repository.getData()).thenReturn(Single.just(emptyList()))

        // when
        presenter.onResume()

        // then
        verify(view).showLoading()
        verify(view).showEmptyList()
        verify(view, never()).showError()
        verify(view, never()).update(anyListOf(String::class.java))
    }

    @Before
    fun setup() {
        val scopeProvider = TestLifecycleScopeProvider.create()
        presenter = MainActivityPresenter(
            lifecycle,
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

    private fun createData(): String {
        return "asdf"
    }
}