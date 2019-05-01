package com.kau.movie_search.server

import android.widget.Toast
import com.kau.movie_search.chatbot.ChatBotActivity
import com.kau.movie_search.chatbot.ChatData
import com.kau.movie_search.chatbot.ChatResponse
import com.kau.movie_search.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerHandler {

    private lateinit var serverInterface : ServerInterface
    private val x_api_key = "mvgYTxG9Mv1kWRjtcnsTH72WIDv5hICI29X0TKrf"

    fun getMovie (movieTitle : String, context : SearchActivity) {
        serverInterface = ServerConstructor ().getService()
        val serverCall = serverInterface.searchMovie (x_api_key, movieTitle);
        serverCall.enqueue(object : Callback<ArrayList<Movie>> {
            override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {
                Toast.makeText(context, "잠시 후 다시 시도 해 주세요.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ArrayList<Movie>>, response: Response<ArrayList<Movie>>) {
                context.movies.clear()
                context.movies.addAll(response.body()!!)
                context.movieAdapter.notifyDataSetChanged()
            }

        })
    }

    fun postChat (chatData : ChatData, context : ChatBotActivity) {
        serverInterface = ServerConstructor().getService()
        val serverCall = serverInterface.chatBot(x_api_key, chatData)
        serverCall.enqueue(object : Callback<ChatResponse> {
            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                println (t)
            }

            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                println (response.body())
                val chatResponse = ChatData ("1", response.body()!!.reply)
                context.chats.add(chatResponse)
                context.chatAdapter.notifyDataSetChanged()
                context.chatLayoutManager.scrollToPosition(context.chats.size - 1)
            }

        })
    }

    fun getDetail (movieData : Movie, context : DetailActivity) {
        serverInterface = ServerConstructor().getService()
        val serverCall = serverInterface.getMovieDetail(x_api_key, DetailRequest (movieData.title, movieData.pubDate))
        serverCall.enqueue (object : Callback<MovieDetail> {
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                println (t)
            }

            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                var platform = ""
                for (i in response.body()!!.platform) {
                    platform += when (i) {
                        8 -> "Netflix/"
                        97 -> "Watcha/"
                        3 -> "Google play movie/"
                        95 -> "pooq/"
                        96 -> "Naver store/"
                        119 -> "Amazon Prime Video/"
                        11 -> "Mubi/"
                        100 -> "Guidedoc/"
                        else -> ""
                    }
                }
                if (platform.isNotEmpty()) {
                    platform = platform.substring(0, platform.length - 1)
                }
                context.detailPlatform.text = platform
            }

        })
    }
}
