package com.findly.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Wojdor on 20.04.2018.
 */

object AllegroService {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    private const val API_KEY = "Api-Key"
    private const val KEY = "eyJjbGllbnRJZCI6ImE0MWY1YjJhLThlODctNGI4Yi1iNmZlLTc0Y2M3NjM3MjBkNyJ9.ogVV_a9RUOMa1OWFZOTmgTkdk-U37vTliDCBUQ1YySU="
    private const val USER_AGENT_KEY = "User-Agent"
    private const val USER_AGENT = "hackaton2017 (Client-Id 656cbe47-b17d-46c2-bae1-3222c8777d5b) Platform"
    private const val ACCEPT_KEY = "Accept"
    private const val ACCEPT = "application/vnd.allegro.public.v1+json"

    fun getApiService(): AllegroApi {
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor {
                    val original = it.request()
                    val request = original.newBuilder()
                            .addHeader(API_KEY, KEY)
                            .addHeader(USER_AGENT_KEY, USER_AGENT)
                            .addHeader(ACCEPT_KEY, ACCEPT)
                            .build()
                    it.proceed(request)
                }.build()
        return Retrofit.Builder()
                .baseUrl(AllegroApi.BASE_URL).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AllegroApi::class.java)
    }
}