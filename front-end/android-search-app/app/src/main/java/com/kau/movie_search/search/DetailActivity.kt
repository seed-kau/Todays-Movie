package com.kau.movie_search.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.kau.movie_search.R
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var movieData : Movie
    private val serverHandler = ServerHandler()
    lateinit var detailImage : ImageView
    lateinit var detailPlatform : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initData()
        initView()
        aboutView()
    }

    private fun initView () {
        detailImage = findViewById(R.id.detailImage)
        detailPlatform = findViewById(R.id.detailPlatform)
    }

    private fun aboutView () {
        val director = if (movieData.director.isNotEmpty()) {
            movieData.director.substring(0, movieData.director.length - 1)
        } else {
            ""
        }

        val actorList = movieData.actor.split("|")
        var actors = ""
        if (actorList.size > 2) {
            for (i in 0..2) {
                actors += actorList[i]
            }
        } else {
            actors = movieData.actor
        }
        actors = actors.substring(0, actors.length - 1)

        detailTitle.text = movieData.title
        detailSubTitle.text = movieData.subTitle
        detailPubDate.text = movieData.pubDate
        detailDirector.text = director
        detailActor.text = actors
        detailUserRating.text = movieData.userRating

        getImage (movieData, detailImage)

        getPlatform()
    }

    private fun getImage (movieData : Movie, imageView : ImageView) {
        DownloadImageTask (imageView).execute(movieData.image)
    }

    private fun getPlatform () {
        serverHandler.getDetail(movieData, this)
    }

    private fun initData () {
        val bundle = intent?.getBundleExtra("myBundle")
        movieData = bundle?.getParcelable("movieData") as Movie
    }
}
