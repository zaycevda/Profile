package com.example.data.utils

import android.content.Context

class TokenSharedPrefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES,
        Context.MODE_PRIVATE
    )

    var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN, null)
        set(value) {
            value?.let {
                sharedPreferences.edit()
                    .putString(ACCESS_TOKEN, it)
                    .apply()
            }
        }

    var refreshToken: String?
        get() = sharedPreferences.getString(REFRESH_TOKEN, null)
        set(value) {
            value?.let {
                sharedPreferences.edit()
                    .putString(REFRESH_TOKEN, it)
                    .apply()
            }
        }

    private companion object {
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
    }
}