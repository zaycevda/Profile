package com.example.data.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class HeaderInterceptor(
    private val tokenSharedPrefs: TokenSharedPrefs,
    private val tokenProvider: dagger.Lazy<TokenProvider>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenSharedPrefs.accessToken

        val requestBuilder = originalRequest.newBuilder()

        if (shouldAddToken(originalRequest.url.toString()))
            requestBuilder.addHeader(AUTHORIZATION, "$BEARER $accessToken")

        val modifiedRequest = requestBuilder.build()

        var response = chain.proceed(modifiedRequest)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            tokenProvider.get().refreshToken()

            val newAccessToken = tokenSharedPrefs.accessToken
            val retryRequestBuilder = originalRequest.newBuilder()
                .addHeader(AUTHORIZATION, "$BEARER $newAccessToken")
            val retryRequest = retryRequestBuilder.build()

            response.close()

            response = chain.proceed(retryRequest)
        }

        return response
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