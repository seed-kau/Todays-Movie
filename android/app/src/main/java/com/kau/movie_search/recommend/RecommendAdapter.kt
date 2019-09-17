package com.kau.movie_search.recommend

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kau.movie_search.R
import com.kau.movie_search.search.DetailActivity
import com.kau.movie_search.search.Movie

class RecommendAdapter (private val recommends : ArrayList<RecommendObject>, val context : DetailActivity) : RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecommendAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate (R.layout.item_recommend, parent, false)

        return RecommendAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommends.size
    }

    override fun onBindViewHolder(holder: RecommendAdapter.ViewHolder, position: Int) {
        val temp = recommends[position]
        println (position)
        println (temp)
        getImage(temp.posters, holder)
        holder.textViewRecommendTitle.text = temp.title
        holder.textViewRecommendYear.text = temp.prodYear
        holder.textViewRecommendGenre.text = temp.genre

        holder.view.setOnClickListener {
            val intent = Intent (context, DetailActivity::class.java)
            val bundle = Bundle()
            val movieData = Movie(title=temp.title, image=temp.posters, pubDate=temp.prodYear)
            bundle.putParcelable("movieData", movieData)
            intent.putExtra("myBundle", bundle)
            context.startActivity(intent)
            context.finish()
        }

    }

    private fun getImage (image : String, holder : RecommendAdapter.ViewHolder) {
        DownloadImageTask (holder.imageViewRecommend).execute(image)
    }

    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val imageViewRecommend : ImageView = view.findViewById(R.id.imageViewRecommend)
        val textViewRecommendTitle : TextView = view.findViewById(R.id.textViewRecommendTitle)
        val textViewRecommendYear : TextView = view.findViewById(R.id.textViewRecommendYear)
        val textViewRecommendGenre : TextView = view.findViewById(R.id.textViewRecommendGenre)
    }
}