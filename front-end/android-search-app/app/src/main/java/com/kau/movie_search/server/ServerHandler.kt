package com.kau.movie_search.server

import android.widget.Toast
import com.kau.movie_search.search.Movie
import com.kau.movie_search.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerHandler {

    private lateinit var serverInterface : ServerInterface

    fun getMovie (movieName : String, context : SearchActivity) {
        serverInterface = ServerConstructor ().getService()
        val serverCall = serverInterface.searchMovie (movieName);
        serverCall.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(context, "잠시 후 다시 시도 해 주세요.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                context.movie = response.body()!!
            }

        })
    }
}