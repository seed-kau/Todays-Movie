package com.kau.movie_search.server

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServerConstructor {

    private var url : String = ""

    fun getService () : ServerInterface {
        url = "https://w2wg7nk6bj.execute-api.us-east-1.amazonaws.com"
        val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

        val gsonBuilder = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build()

        return retrofit.create(ServerInterface::class.java)
    }

}