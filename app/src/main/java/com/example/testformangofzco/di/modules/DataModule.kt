package com.example.testformangofzco.di.modules

import android.content.Context
import com.example.data.repository.ApiRepositoryImpl
import com.example.data.service.Api
import com.example.data.utils.HeaderInterceptor
import com.example.data.utils.TokenProvider
import com.example.data.utils.TokenSharedPrefs
import com.example.domain.repository.ApiRepository
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideApiRepository(
        api: Api,
        context: Context
    ): ApiRepository = ApiRepositoryImpl(
        api = api,
        context = context
    )

    @Provides
    @Singleton
    fun provideApi(
        retrofit: Retrofit
    ): Api = retrofit
        .create(Api::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val cache = Cache(context.cacheDir, CACHE_SIZE)

        return OkHttpClient().newBuilder()
            .addInterceptor(headerInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        tokenSharedPrefs: TokenSharedPrefs,
        tokenProvider: dagger.Lazy<TokenProvider>
    ) = HeaderInterceptor(
        tokenSharedPrefs = tokenSharedPrefs,
        tokenProvider = tokenProvider
    )

    @Provides
    @Singleton
    fun provideTokenSharedPrefs(
        context: Context
    ) = TokenSharedPrefs(
        context = context
    )

    @Provides
    @Singleton
    fun provideTokenProvider(
        tokenSharedPrefs: TokenSharedPrefs,
        api: Api
    ) = TokenProvider(
        tokenSharedPrefs = tokenSharedPrefs,
        api = api
    )

    private companion object {
        private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB
        private const val BASE_URL = "https://plannerok.ru"
    }
}