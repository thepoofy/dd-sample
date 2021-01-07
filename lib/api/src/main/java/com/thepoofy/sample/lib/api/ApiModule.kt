package com.thepoofy.sample.lib.api

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiModule {

    /**
     * Prefer createSynchronous over create so that Rx streams are forced to use consistent language
     * with regards to subscribeOn/observeOn.
     */
    @Provides
    fun adapterFactory(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.createSynchronous()

    @Provides
    fun sampleApi(
        adapterFactory: RxJava3CallAdapterFactory,
        moshi: Moshi,
    ): DoorDashApi {
        val httpClient = OkHttpClient.Builder().build()

        val converterFactory = MoshiConverterFactory.create(moshi)

        return Retrofit.Builder()
            .baseUrl(DoorDashApi.API_ROOT)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(adapterFactory)
            .client(httpClient)
            .build()
            .create(DoorDashApi::class.java)
    }
}