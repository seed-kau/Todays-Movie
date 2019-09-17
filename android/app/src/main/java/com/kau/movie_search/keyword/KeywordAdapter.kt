package com.kau.movie_search.keyword

import android.annotation.SuppressLint
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

class KeywordAdapter (val keywords : ArrayList<MovieKeyword>, val context : KeywordActivity) : RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): KeywordAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate (R.layout.item_keyword, parent, false)

        return KeywordAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return keywords.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KeywordAdapter.ViewHolder, position: Int) {
        val keyword = keywords[position]
        holder.itemKeywordTitle.text = keyword.title
        keyword.release_date = keyword.release_date.split("-")[0]
        holder.itemKeywordYear.text = "(" + keyword.release_date  +")"
        holder.itemKeywordDes.text = keyword.overview

        if (keyword.adult) {
            holder.imageKeyword.visibility = View.VISIBLE
        } else {
            holder.imageKeyword.visibility = View.INVISIBLE
        }
        holder.view.setOnClickListener {
            val intent = Intent (context, DetailActivity::class.java)
            val bundle = Bundle()
            val movieData = Movie(title=keyword.title, pubDate=keyword.release_date)
            bundle.putParcelable("movieData", movieData)
            intent.putExtra("myBundle", bundle)
            context.startActivity(intent)
        }
    }


    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val itemKeywordTitle : TextView = view.findViewById(R.id.itemKeywordTitle)
        val itemKeywordYear : TextView = view.findViewById(R.id.itemKeywordYear)
        val imageKeyword : ImageView = view.findViewById(R.id.imageKeyword)
        val itemKeywordDes : TextView = view.findViewById(R.id.itemKeywordDes)
    }
}