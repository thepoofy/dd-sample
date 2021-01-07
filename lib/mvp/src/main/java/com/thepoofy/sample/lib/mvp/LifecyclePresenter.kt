package com.thepoofy.sample.lib.mvp

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import java.util.concurrent.atomic.AtomicBoolean

open class LifecyclePresenter<VB : ViewBinding> : LifecycleObserver {

    private val isInitialized = AtomicBoolean(false)

    @CallSuper
    open fun onCreateView(binding: VB) {
        if (!isInitialized.compareAndSet(false, true)) {
            throw RuntimeException("onCreate was already called for this Presenter.")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny() {
        if (!isInitialized.get()) {
            throw RuntimeException("Presenter not initialized before attached to lifecycle. " + this.javaClass.name)
        }
    }
}