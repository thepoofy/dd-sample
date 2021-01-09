package com.thepoofy.sample.lib.api

import com.thepoofy.sample.lib.api.form.LoginForm
import com.thepoofy.sample.lib.api.model.AuthResponse
import com.thepoofy.sample.lib.api.model.Restaurant
import com.thepoofy.sample.lib.api.model.RestaurantDetails
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface DoorDashApi {

    @GET("restaurant/")
    fun getRestaurants(
        @Query("lat") latitude: Float,
        @Query("lng") longitude: Float,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Single<List<Restaurant>>

    @GET("restaurant/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Single<RestaurantDetails>

    @POST("auth/token/")
    fun login(
        @Body form: LoginForm
    ): Single<AuthResponse>

    companion object {
        const val API_ROOT = "https://api.doordash.com/v2/"
    }

}