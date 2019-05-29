package com.kau.movie_search.server

import com.kau.movie_search.chatbot.ChatData
import com.kau.movie_search.chatbot.ChatResponse
import com.kau.movie_search.keyword.KeywordRequest
import com.kau.movie_search.keyword.MovieKeyword
import com.kau.movie_search.search.DetailRequest
import com.kau.movie_search.search.Movie
import com.kau.movie_search.search.MovieDetail
import retrofit2.Call
import retrofit2.http.*

interface ServerInterface {
    @GET("/Development/movies/search")
    fun searchMovie (@Header ("x-api-key") key : String, @Query("title") title : String) : Call<ArrayList<Movie>>

    @POST ("/Development/lex")
    fun chatBot (@Header ("x-api-key") key : String, @Body chatData : ChatData) : Call<ChatResponse>

    @POST ("/Development/movies/search/platform")
    fun getMovieDetail (@Header ("x-api-key") key : String, @Body detailRequest : DetailRequest) : Call<MovieDetail>

    @POST ("/Development/movies/search/keyword")
    fun getKeyWord (@Header ("x-api-key") key : String, @Body keywordRequest : KeywordRequest) : Call<ArrayList<MovieKeyword>>
}