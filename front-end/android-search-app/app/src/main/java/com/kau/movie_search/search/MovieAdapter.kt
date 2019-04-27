package com.kau.movie_search.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kau.movie_search.R

class MovieAdapter (val platforms : ArrayList<Int>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate (R.layout.item_movie_search, parent, false)

        return MovieAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return platforms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val platform: String = when (platforms[position]) {
            8 -> "Netflix"
            97 -> "Watcha"
            3 -> "Google play movie"
            95 -> "pooq"
            96 -> "Naver store"
            119 -> "Amazon Prime Video"
            11 -> "Mubi"
            100 -> "Guidedoc"
            else -> ""
        }
        holder.textViewPlatform.text = platform
    }

    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val textViewPlatform : TextView = view.findViewById(R.id.textViewPlatform)
    }
}