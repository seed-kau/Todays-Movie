package com.kau.movie_search.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kau.movie_search.R
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    var movie = Movie()
    private val serverHandler = ServerHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        aboutView()
    }

    private fun aboutView () {
        btnSearch.setOnClickListener {
            val movieName = editMovie.text.toString()
            if (movieName == "") {
                Toast.makeText(this, "영화 이름을 입력 해 주세요.", Toast.LENGTH_LONG).show()
            } else {
                serverHandler.getMovie(movieName, this)
            }
        }
    }
}
