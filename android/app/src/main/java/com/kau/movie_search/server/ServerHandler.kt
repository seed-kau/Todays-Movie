package com.kau.movie_search.server

import android.text.Html
import android.widget.Toast
import com.kau.movie_search.R
import com.kau.movie_search.chatbot.ChatBotActivity
import com.kau.movie_search.chatbot.ChatData
import com.kau.movie_search.chatbot.ChatResponse
import com.kau.movie_search.keyword.KeywordActivity
import com.kau.movie_search.keyword.KeywordRequest
import com.kau.movie_search.keyword.MovieKeyword
import com.kau.movie_search.recommend.RecommendResponse
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
                println (t)
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
                val chatResponse = ChatData ("1", response.body()!!.reply, -1, response.body()!!.url, response.body()!!.isFinish, response.body()!!.year)
                context.chats.add(chatResponse)
                context.chatAdapter.notifyDataSetChanged()
                context.chatLayoutManager.scrollToPosition(context.chats.size - 1)
            }

        })
    }

    fun getDetail (movieData : Movie, context : DetailActivity) {
        serverInterface = ServerConstructor().getService()
        val serverCall = serverInterface.getMovieDetail(x_api_key, DetailRequest (Html.fromHtml(movieData.title).toString(), movieData.pubDate.toInt()))
        serverCall.enqueue (object : Callback<ArrayList<MovieDetail>> {
            override fun onFailure(call: Call<ArrayList<MovieDetail>>, t: Throwable) {
                Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                println (t)
            }

            override fun onResponse(call: Call<ArrayList<MovieDetail>>, response: Response<ArrayList<MovieDetail>>) {
                movieData.platformList.clear()
                for (i in response.body()!!) {
                    movieData.platformList.add(when (i.platform_number) {
                        8 -> R.drawable.netflix
                        97 -> R.drawable.watcha
                        3 -> R.drawable.google_movie
                        95 -> R.drawable.pooq
                        96 -> R.drawable.n_store
                        119 -> R.drawable.amazon
                        11 -> R.drawable.mubi
                        100 -> R.drawable.guidedoc
                        else -> 0
                    })
                }
                context.platFormAdapter.notifyDataSetChanged()
            }

        })
    }

    fun getKeyword (keywordRequest : KeywordRequest, context : KeywordActivity) {
        serverInterface = ServerConstructor().getService()
        val serverCall = serverInterface.getKeyWord(x_api_key, keywordRequest)
        serverCall.enqueue (object : Callback<ArrayList<MovieKeyword>> {
            override fun onFailure(call: Call<ArrayList<MovieKeyword>>, t: Throwable) {
                println (t)
            }

            override fun onResponse(call: Call<ArrayList<MovieKeyword>>, response: Response<ArrayList<MovieKeyword>>) {
                context.keywords.clear()
                context.keywords.addAll(response.body()!!)
                context.keywordAdapter.notifyDataSetChanged()
            }

        })
    }

    fun getRecommend (movieData : Movie, context : DetailActivity) {
        serverInterface = ServerConstructor().getService()
        println(movieData.title)
        val serverCall = serverInterface.getRecommend(x_api_key, DetailRequest (Html.fromHtml(movieData.title).toString(), movieData.pubDate.toInt()))
        serverCall.enqueue(object : Callback<RecommendResponse> {
            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                println (t)
            }

            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                context.recommends.clear()
                if (response.body()!!.output != null) {
                    context.recommends.addAll(response.body()!!.output)
                }
                context.recommendAdapter.notifyDataSetChanged()
            }

        })
    }
}
