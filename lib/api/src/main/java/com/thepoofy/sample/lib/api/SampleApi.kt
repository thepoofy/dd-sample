package com.thepoofy.sample.lib.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface SampleApi {

    @GET("example.json")
    fun getData(): Single<List<String>>

    companion object {
        const val API_ROOT = "https://example.com/"
    }
}