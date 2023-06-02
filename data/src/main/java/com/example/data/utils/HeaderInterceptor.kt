package com.example.data.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.data.repository.ApiRepositoryImpl.Companion.ACCESS_TOKEN
import com.example.data.repository.ApiRepositoryImpl.Companion.BLANK
import com.example.data.repository.ApiRepositoryImpl.Companion.SHARED_PREFERENCES
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(context: Context) : Interceptor {

    private var previousCall: Call? = null
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        previousCall?.cancel()

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (shouldAddToken(originalRequest.url.toString()))
            sharedPreferences.getString(ACCESS_TOKEN, BLANK)?.let { accessToken ->
                val modifiedRequest = requestBuilder
                    .addHeader(AUTHORIZATION, "$BEARER $accessToken")
                    .build()

                previousCall = chain.call()

                return@let chain.proceed(modifiedRequest)
            }

        previousCall = chain.call()

        return chain.proceed(originalRequest)
    }

    private fun shouldAddToken(url: String) =
        !(url == URL_REGISTER ||
        url == URL_SEND_AUTH_CODE ||
        url == URL_CHECK_AUTH_CODE)

    private companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val URL_REGISTER = "https://plannerok.ru/api/v1/users/register/"
        private const val URL_SEND_AUTH_CODE = "https://plannerok.ru/api/v1/users/send-auth-code/"
        private const val URL_CHECK_AUTH_CODE = "https://plannerok.ru/api/v1/users/check-auth-code/"
    }
}