package com.kau.movie_search.search

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    private val serverHandler = ServerHandler()
    lateinit var movieAdapter : MovieAdapter
    private lateinit var movieLayoutManager : RecyclerView.LayoutManager
    lateinit var spinnerSortList : ArrayList<String>
    lateinit var spinnerSortAdapter : ArrayAdapter<String>
    val movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kau.movie_search.R.layout.activity_search)

        initSpinner()
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

    private fun initSpinner () {
        spinnerSortList = ArrayList()
        spinnerSortList.add("제목 순")
        spinnerSortList.add("평점 순")
        spinnerSortList.add("개봉년도 순")

        spinnerSortAdapter = ArrayAdapter(this, com.kau.movie_search.R.layout.support_simple_spinner_dropdown_item, spinnerSortList)

        spinnerSort.adapter = spinnerSortAdapter


    }

    private fun aboutView () {
        btnSearch.setOnClickListener {
            getMovie()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editMovie.windowToken, 0)
        }

        editMovie.setOnEditorActionListener { v, actionId, event ->
            getMovie()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editMovie.windowToken, 0)
            true
        }


        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                movies.sortBy { it.title }
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        movies.sortBy { it.title }
                    }
                    1 -> {
                        movies.sortBy { it.userRating }
                        movies.reverse()
                    }
                    2 -> {
                        movies.sortBy { it.pubDate }
                        movies.reverse()
                    }
                }
                movieAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getMovie () : Boolean {
        val movieTitle = editMovie.text.toString()
        return if (movieTitle == "") {
            Toast.makeText(this, "영화 이름을 입력 해 주세요.", Toast.LENGTH_LONG).show()
            true
        } else {
            serverHandler.getMovie(movieTitle, this)
            false
        }
    }


}
