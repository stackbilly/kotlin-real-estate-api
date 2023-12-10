package com.stackbilly.mainapplication.retrofit

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

//adding authentication headers to the HTTP requests.
class AuthInterceptor(context: Context) : Interceptor {
    private  val sessionManager = SessionManager(context)





    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        //adding the token to the request if its saved
        sessionManager.fetchAuthToken()?.let {
            requestBuilder
                .addHeader("Authentication", "Bearer $it")
                .addHeader("Email", "eddie@gmail.com")
                .addHeader("TokenID", it)
        }
        return chain.proceed(requestBuilder.build())
    }

}