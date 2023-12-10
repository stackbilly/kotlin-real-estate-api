package com.stackbilly.mainapplication.retrofit

import android.content.Context
import android.content.SharedPreferences


class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences("Tusomi Parent", Context.MODE_PRIVATE)

    private var accessToken: String? = null
    private var accessTokenExpiration: Long? = null

    fun isAccessTokenExpired():Boolean{
        val currentTimeMillis = System.currentTimeMillis()
        return accessTokenExpiration != null && currentTimeMillis >= accessTokenExpiration!!
    }

    fun updateAccessToken(token: String, expiresIn: Long){
        accessToken = token
        accessTokenExpiration = System.currentTimeMillis() + expiresIn * 1000
    }


    companion object {
        const val USER_TOKEN = "token"
    }

    //saving auth token

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    //fetching auth token

    fun fetchAuthToken(): String? {
        return prefs.getString("token", null)
    }
}