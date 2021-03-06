package com.kau.movie_search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kau.movie_search.chatbot.ChatBotActivity
import com.kau.movie_search.keyword.KeywordActivity
import com.kau.movie_search.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        aboutView()
    }

    private fun aboutView () {
        btnToSearch.setOnClickListener {
            val intent = Intent (this, SearchActivity::class.java)
            startActivity (intent)
        }

        btnToChat.setOnClickListener {
            val intent = Intent (this, ChatBotActivity::class.java)
            startActivity(intent)
        }

        btnToKeyword.setOnClickListener {
            val intent = Intent (this, KeywordActivity::class.java)
            startActivity(intent)
        }
    }
}
