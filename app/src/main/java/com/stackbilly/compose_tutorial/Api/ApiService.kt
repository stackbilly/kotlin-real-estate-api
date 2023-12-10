package com.stackbilly.compose_tutorial.Api

import com.stackbilly.compose_tutorial.models.ApiHouseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @GET("api/houses")
    @Headers("Content-Type: application/json")
    fun getHouses():Call<ApiHouseResponse>
}
