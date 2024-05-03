package com.example.newsapp.api

import android.util.Log
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private lateinit var retrofit: Retrofit


    init {

        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {

                Log.e("api->", message)
            }
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)


        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getServices(): WebServices {
        return retrofit.create(WebServices::class.java)
    }

}