package com.thepoofy.sample.lib.api

import com.thepoofy.sample.lib.api.model.Restaurant
import com.thepoofy.sample.lib.api.model.RestaurantDetails
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoorDashApi {

    @GET("restaurant/")
    fun searchRestaurants(
        @Query("lat") latitude: Float,
        @Query("lng") longitude: Float,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Single<List<Restaurant>>

    @GET("restaurant/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Single<RestaurantDetails>

    companion object {
        const val API_ROOT = "https://api.doordash.com/v2/"
    }
}