package com.example.api.api

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

internal class CocktailsService {
    companion object {
        fun getApi(): CocktailAPI {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
                .addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ResultCallAdapterFactory.create())
                .client(builder.build())
                .build()
                .create(CocktailAPI::class.java)
        }
    }
}

