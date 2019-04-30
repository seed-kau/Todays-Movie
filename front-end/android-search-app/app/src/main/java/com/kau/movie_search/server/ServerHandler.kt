package com.kau.movie_search.server

import android.widget.Toast
import com.kau.movie_search.chatbot.ChatBotActivity
import com.kau.movie_search.chatbot.ChatData
import com.kau.movie_search.chatbot.ChatResponse
import com.kau.movie_search.search.Movie
import com.kau.movie_search.search.SearchActivity
import kotlinx.android.synthetic.main.activity_chatbot.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerHandler {

    private lateinit var serverInterface : ServerInterface

    fun getMovie (movieName : String, context : SearchActivity) {
        serverInterface = ServerConstructor ().getService("")
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

    fun postChat (chatData : ChatData, context : ChatBotActivity) {
        serverInterface = ServerConstructor().getService("chat")
        val serverCall = serverInterface.chatBot("mvgYTxG9Mv1kWRjtcnsTH72WIDv5hICI29X0TKrf", chatData)
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
}
