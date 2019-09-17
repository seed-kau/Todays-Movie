package com.kau.movie_search.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kau.movie_search.R

class PlatformAdapter (val platforms : ArrayList<Int>, val context : DetailActivity) : RecyclerView.Adapter<PlatformAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PlatformAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate (R.layout.item_platform, parent, false)

        return PlatformAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return platforms.size
    }

    override fun onBindViewHolder(holder: PlatformAdapter.ViewHolder, position: Int) {
        val temp = platforms[position]
        holder.imageViewPlayform.setImageResource(temp)
    }

    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val imageViewPlayform : ImageView = view.findViewById(R.id.imageViewPlatform)
    }
}