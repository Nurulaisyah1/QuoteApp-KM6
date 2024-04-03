package com.quoteapp.data.source

import com.quoteapp.BuildConfig
import com.quoteapp.data.source.network.model.QuoteResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface QuoteApiServices {

    @GET("quotes")
    suspend fun getRandomQuotes(): List<QuoteResponse>

    companion object {
        @JvmStatic
        operator fun invoke(): QuoteApiServices {
            val OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build()
            return Retrofit.create(QuoteApiServices::class.java)
        }
    }
}
