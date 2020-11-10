package com.oreilly.icndb

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AstroService {

    @GET("jokes/random")
    suspend fun getResponse(
        @Query("limitTo") limitTo: String = "[nerdy]",
        @Query("firstName") firstName: String = "Ben",
        @Query("lastName") lastName: String = "Sisko"
    ): JokeResponse

    companion object {
        private const val BASE = "http://api.icndb.com/"

        fun create(): AstroService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AstroService::class.java)
        }
    }
}