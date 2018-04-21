package com.findly.application.data.googleApi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by patrykgalazka on 20.04.2018.
 */
object VisionApiService {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    fun getApiService(): VisionApi {
        var client = OkHttpClient.Builder()
                .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build()
        var service: VisionApi = Retrofit.Builder()
                .baseUrl(VisionApi.API_VISION_URL).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(VisionApi::class.java)
        return service
    }
}
