package com.thepoofy.sample.features.main_activity

import io.reactivex.rxjava3.core.Observable

interface LocationProvider {
    fun getCurrentLocation(): Observable<Pair<Float, Float>>
}