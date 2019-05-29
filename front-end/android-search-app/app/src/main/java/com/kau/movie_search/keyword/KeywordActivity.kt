package com.kau.movie_search.keyword

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import com.kau.movie_search.R
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_keyword.*
import kotlinx.android.synthetic.main.activity_search.*

class KeywordActivity : AppCompatActivity() {

    lateinit var keywordAdapter : KeywordAdapter
    lateinit var keywordLayoutManager : RecyclerView.LayoutManager
    val keywords = ArrayList<MovieKeyword>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        initRecycler()
        aboutView()
    }

    private fun initRecycler () {
        keywordAdapter = KeywordAdapter (keywords, this)
        keywordLayoutManager = LinearLayoutManager(this)

        recyclerKeyword.adapter = keywordAdapter
        recyclerKeyword.layoutManager = keywordLayoutManager

        recyclerKeyword.setHasFixedSize(true)

    }

    private fun aboutView () {
        btnKeyword.setOnClickListener {
            getKeyword (editTextKeyword.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editTextKeyword.windowToken, 0)
        }

        editTextKeyword.setOnEditorActionListener { v, actionId, event ->
            getKeyword (editTextKeyword.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editMovie.windowToken, 0)
            true
        }
    }

    private fun getKeyword (keyword : String) {
        val serverHandler = ServerHandler ()
        serverHandler.getKeyword(KeywordRequest(keyword), this)
    }
}
