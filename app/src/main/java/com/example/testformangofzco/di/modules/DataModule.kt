package com.example.testformangofzco.di.modules

import android.content.Context
import com.example.data.repository.ApiRepositoryImpl
import com.example.data.service.Api
import com.example.data.utils.HeaderInterceptor
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
    fun provideApi(context: Context): Api {
        val cache = Cache(context.cacheDir, CACHE_SIZE)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(HeaderInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(Api::class.java)
    }

    private companion object {
        private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB
        private const val BASE_URL = "https://plannerok.ru"
    }
}