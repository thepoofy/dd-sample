package com.thepoofy.sample.features.main_activity

import com.thepoofy.sample.lib.api.DoorDashApi
import com.thepoofy.sample.lib.api.model.Restaurant
import io.reactivex.rxjava3.core.Single

class DataListRepositoryImpl constructor(
    private val api: DoorDashApi,
) : DataListRepository {

    override fun getRestaurants(
        latitude: Float,
        longitude: Float,
        offset: Int,
        limit: Int,
    ): Single<List<Restaurant>> {
        return api.searchRestaurants(latitude, longitude, offset, limit)
    }
}