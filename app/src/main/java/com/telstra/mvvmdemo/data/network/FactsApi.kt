package com.telstra.mvvmdemo.data.network

import com.telstra.mvvmdemo.data.model.Facts
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 *  Created Interface for Network Operations
 *  Created Companion object to Initialize Retrofit
 */
interface FactsApi {

    @GET("facts.json")
    suspend fun getData(): Response<Facts>

    companion object {
        private const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

        operator fun invoke(): FactsApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FactsApi::class.java)
        }
    }
}