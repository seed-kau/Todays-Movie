package com.kau.movie_search.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.kau.movie_search.R
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private val serverHandler = ServerHandler()
    lateinit var movieAdapter : MovieAdapter
    private lateinit var movieLayoutManager : RecyclerView.LayoutManager
    val movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initRecycler()
        aboutView()
    }

    private fun initRecycler () {
        movieAdapter = MovieAdapter(movies, this)
        movieLayoutManager = LinearLayoutManager(this)

        recyclerMovie.adapter = movieAdapter
        recyclerMovie.layoutManager = movieLayoutManager

        recyclerMovie.setHasFixedSize(true)
    }

    private fun aboutView () {
        btnSearch.setOnClickListener {
            val movieTitle = editMovie.text.toString()
            if (movieTitle == "") {
                Toast.makeText(this, "영화 이름을 입력 해 주세요.", Toast.LENGTH_LONG).show()
            } else {
                serverHandler.getMovie(movieTitle, this)
            }
        }
    }


}
