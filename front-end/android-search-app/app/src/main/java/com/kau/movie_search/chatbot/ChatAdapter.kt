package com.kau.movie_search.chatbot

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kau.movie_search.R

class ChatAdapter (val chats : ArrayList<ChatData>, val randomNumber : Int) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageResource  : Int = when (randomNumber) {
            0 -> {
                R.drawable.boribap
            } 1 -> {
                R.drawable.daecheol
            } 2-> {
                R.drawable.juhyang
            } else -> {
                R.drawable.boribap
            }
        }

        holder.chatImage.setImageResource (imageResource)

        val chatData = chats[position]
        if (chatData.id == "1") {
            holder.textViewChatBot.visibility = View.VISIBLE
            holder.chatImage.visibility = View.VISIBLE
            holder.textViewChatUser.visibility = View.INVISIBLE

            holder.textViewChatBot.text = chatData.message
        } else {
            holder.textViewChatBot.visibility = View.INVISIBLE
            holder.chatImage.visibility = View.INVISIBLE
            holder.textViewChatUser.visibility = View.VISIBLE

            holder.textViewChatUser.text = chatData.message
        }
    }


    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val textViewChatBot : TextView = view.findViewById(R.id.textViewChatBot)
        val textViewChatUser : TextView = view.findViewById(R.id.textViewChatUser)
        val chatImage : ImageView = view.findViewById(R.id.chatImage)
    }
}