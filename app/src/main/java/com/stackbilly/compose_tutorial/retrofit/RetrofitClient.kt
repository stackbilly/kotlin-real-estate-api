package com.stackbilly.compose_tutorial.retrofit

import android.content.Context
import com.stackbilly.compose_tutorial.Api.ApiService
import com.stackbilly.mainapplication.retrofit.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private lateinit var apiService: ApiService

    fun getApiService(context: Context):ApiService{
        if (!::apiService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl("https://cd13-154-79-142-87.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }
    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}