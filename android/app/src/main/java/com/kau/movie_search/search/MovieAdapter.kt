package com.kau.movie_search.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kau.movie_search.R

@Suppress("DEPRECATION")
class MovieAdapter (val movies : ArrayList<Movie>, val context : SearchActivity) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate (R.layout.item_movie_search, parent, false)

        return MovieAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieData = movies[position]
        val director : String = if (movieData.director.isNotEmpty()) {
            movieData.director.substring(0, movieData.director.length - 1)
        } else {
            movieData.director
        }
        holder.searchTitle.text = Html.fromHtml(movieData.title)
        holder.searchPubDate.text = movieData.pubDate
        holder.searchDirector.text = director
        holder.searchUserRating.text = movieData.userRating
        getImage(movieData, holder)

        holder.view.setOnClickListener {
            val intent = Intent (context, DetailActivity::class.java)
            var bundle = Bundle()
            bundle.putParcelable("movieData", movieData)
            intent.putExtra("myBundle", bundle)
            context.startActivity(intent)
        }
    }

    private fun getImage (movieData : Movie, holder : ViewHolder) {
        DownloadImageTask (holder.searchImage).execute(movieData.image)
    }

    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val searchTitle : TextView = view.findViewById(R.id.searchTitle)
        val searchImage : ImageView = view.findViewById(R.id.searchImage)
        val searchPubDate : TextView = view.findViewById(R.id.searchPubDate)
        val searchDirector : TextView = view.findViewById(R.id.searchDirector)
        val searchUserRating : TextView = view.findViewById(R.id.searchUserRating)
    }
}