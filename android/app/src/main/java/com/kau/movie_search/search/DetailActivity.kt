package com.kau.movie_search.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.widget.ImageView
import com.kau.movie_search.R
import com.kau.movie_search.recommend.RecommendAdapter
import com.kau.movie_search.recommend.RecommendObject
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_detail.*

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    lateinit var movieData : Movie
    private val serverHandler = ServerHandler()
    lateinit var detailImage : ImageView

    lateinit var platFormAdapter : PlatformAdapter
    lateinit var platformLayoutManager : RecyclerView.LayoutManager

    var recommends : ArrayList<RecommendObject> = ArrayList()
    lateinit var recommendAdapter : RecommendAdapter
    lateinit var recommendLayoutManager : RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initData()
        initRecycler()
        initView()
        aboutView()
    }

    private fun initRecycler () {
        platFormAdapter = PlatformAdapter(movieData.platformList, this)
        platformLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerDetail.apply {
            adapter = platFormAdapter
            layoutManager = platformLayoutManager
        }

        recommendAdapter = RecommendAdapter(recommends, this)
        recommendLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRecommend.apply {
            adapter = recommendAdapter
            layoutManager = recommendLayoutManager
        }
    }

    private fun initView () {
        detailImage = findViewById(R.id.detailImage)
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
                actors += actorList[i] + "\n"
            }
        } else {
            actors = movieData.actor
        }
        if (actors != "") {
            actors = actors.substring(0, actors.length - 1)
        }


        detailTitle.text = Html.fromHtml(movieData.title)
        detailPubDate.text = movieData.pubDate
        detailDirector.text = director
        detailActor.text = actors
        detailUserRating.text = movieData.userRating

        getImage (movieData, detailImage)

        getPlatform()
        getRecommend()
    }

    private fun getImage (movieData : Movie, imageView : ImageView) {
        DownloadImageTask (imageView).execute(movieData.image)
    }

    private fun getPlatform () {
        serverHandler.getDetail(movieData, this)
    }

    private fun getRecommend () {
        serverHandler.getRecommend(movieData, this)
    }

    private fun initData () {
        val bundle = intent?.getBundleExtra("myBundle")
        movieData = bundle?.getParcelable("movieData") as Movie
    }
}
