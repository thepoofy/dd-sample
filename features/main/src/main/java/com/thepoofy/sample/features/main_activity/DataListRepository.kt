package com.thepoofy.sample.features.main_activity

import com.thepoofy.sample.lib.api.model.Restaurant
import io.reactivex.rxjava3.core.Single

interface DataListRepository {

    fun getRestaurants(
        latitude: Float,
        longitude: Float,
        offset: Int,
        limit: Int,
    ): Single<List<Restaurant>>
}