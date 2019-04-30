package com.kau.movie_search.server

import com.kau.movie_search.chatbot.ChatData
import com.kau.movie_search.chatbot.ChatResponse
import com.kau.movie_search.search.Movie
import retrofit2.Call
import retrofit2.http.*

interface ServerInterface {
    @GET("/movie/search/{title}")
    fun  searchMovie (@Path("title") title : String) : Call<Movie>

    @POST ("/Development/lex")
    fun chatBot (@Header ("x-api-key") key : String, @Body chatData : ChatData) : Call<ChatResponse>
}