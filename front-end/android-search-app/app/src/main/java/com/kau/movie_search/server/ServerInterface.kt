package com.kau.movie_search.server

import com.kau.movie_search.search.Movie
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerInterface {
    @GET("/movie/search/{title}")
    fun  searchMovie (@Path("title") title : String) : Call<Movie>

    @POST ("/movie/chat/conversation")
    fun chatBot (@Body content : String) : Call<String>
}