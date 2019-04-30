package com.kau.movie_search.chatbot

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kau.movie_search.R

class ChatAdapter (val chats : ArrayList<ChatData>, context : Activity) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp = chats[position].message
        holder.textViewChat.text = temp
    }


    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val textViewChat : TextView = view.findViewById(R.id.textViewChat)
    }
}