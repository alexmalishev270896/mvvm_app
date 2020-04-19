package com.alex_malishev.data_layer.utils

import android.app.Application
import com.alex_malishev.data_layer.DataLayer.get
import java.util.*

/**
 * This class helps to store user's token
 */
object UserStorage {
    private const val USER_STORAGE = "ru.aron_stoun.data_layer.user_storage"
    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"

    val accessToken: String
        get() {
            val sPref = get().getSharedPreferences(USER_STORAGE, Application.MODE_PRIVATE)
            return sPref.getString(ACCESS_TOKEN, "")!!
        }

    val refreshToken: String
        get() {
            val sPref = get().getSharedPreferences(USER_STORAGE, Application.MODE_PRIVATE)
            return sPref.getString(REFRESH_TOKEN, "")!!
        }

    fun setToken(accessToken: String?, refreshToken: String?) {
        val sPref = get().getSharedPreferences(USER_STORAGE, Application.MODE_PRIVATE)
        sPref.edit().apply{
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    fun clearToken() {
        val sPref = get()
            .getSharedPreferences(USER_STORAGE, Application.MODE_PRIVATE)
        val ed = sPref.edit()
        ed.clear()
        ed.apply()
    }
}