package com.findly.data.service

import com.findly.data.service.response.AllegroMobileApi
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
    private const val AUTHORIZATION_KEY = "Authorization"
    private const val AUTHORIZATION = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGxlZ3JvX2FwaSJdLCJleHAiOjE1MjQzNDAwMTQsImp0aSI6ImEwZjg5OGMxLTEwNGItNGJkOS05NTRhLTY0NTIwYWY5YjFmMyIsImNsaWVudF9pZCI6ImE0MWY1YjJhLThlODctNGI4Yi1iNmZlLTc0Y2M3NjM3MjBkNyJ9.u3axb3Hj6fSLVKj-owniQ-uQd17uoFaFpDg8b0pkLUfNYG532HY4Cj08ew4ktzZKfBTWmlAuu3Y69CNkoTevoSFxNu_sqkUI7O5tWpculVIauGV7i1HvDqiRP-fLFL8wd76NgDg9MOIhGHLpMCg797Acbxq3Bz0qzXO68C8weNm3XJWanMr2b-6Ztj2VWD7FgfBhqFV_4YasM307TZNAs-GcFZFk-hFo6AGrtlvePSQloptInybrIAE1C35L8ku7VH0o2erf8KTVYxf1Eyb1bc8luzpXNQGzccPRt866cLDYpqzX3VFwc_0aUMPLR250-eRE7cp8F8T9ukRXpUuhzw"

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

    fun getMobileApiService(): AllegroMobileApi {
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor {
                    val original = it.request()
                    val request = original.newBuilder()
                            .addHeader(AUTHORIZATION_KEY, AUTHORIZATION)
                            .build()
                    it.proceed(request)
                }.build()
        return Retrofit.Builder()
                .baseUrl(AllegroMobileApi.BASE_URL).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AllegroMobileApi::class.java)
    }
}