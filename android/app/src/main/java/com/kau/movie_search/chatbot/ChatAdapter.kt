package com.kau.movie_search.chatbot

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.kau.movie_search.R
import com.kau.movie_search.search.DetailActivity
import com.kau.movie_search.search.Movie

class ChatAdapter (private val chats : ArrayList<ChatData>, val randomNumber : Int, val context : ChatBotActivity) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chatPosterImage.visibility = View.GONE

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
            holder.chatLinearLayout.visibility = View.VISIBLE
            holder.textViewChatBot.visibility = View.VISIBLE
            holder.chatImage.visibility = View.VISIBLE
            holder.textViewChatUser.visibility = View.INVISIBLE

            holder.textViewChatBot.text = chatData.message
        } else {
            holder.chatLinearLayout.visibility = View.INVISIBLE
            holder.textViewChatBot.visibility = View.INVISIBLE
            holder.chatImage.visibility = View.INVISIBLE
            holder.textViewChatUser.visibility = View.VISIBLE

            holder.textViewChatUser.text = chatData.message
        }

        if (chatData.isFinish) {
            var title = ""
            for (i in 0 until chatData.message.length) {
                if (chatData.message[i] == '(') {
                    break
                }
                title += chatData.message[i]

            }
            println (title)
            holder.chatPosterImage.visibility = View.VISIBLE
            getImage(chatData.url, holder)
            holder.view.setOnClickListener {
                val intent = Intent (context, DetailActivity::class.java)
                val bundle = Bundle()
                val movieData = Movie(title=title, image=chatData.url, pubDate=chatData.year)
                bundle.putParcelable("movieData", movieData)
                intent.putExtra("myBundle", bundle)
                context.startActivity(intent)
                context.finish()
            }
        }
    }

    private fun getImage (image : String, holder : ChatAdapter.ViewHolder) {
        DownloadImageTask(holder.chatPosterImage).execute(image)
    }


    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val chatLinearLayout : LinearLayout = view.findViewById(R.id.chatLinearLayout)
        val textViewChatBot : TextView = view.findViewById(R.id.textViewChatBot)
        val textViewChatUser : TextView = view.findViewById(R.id.textViewChatUser)
        val chatImage : ImageView = view.findViewById(R.id.chatImage)
        val chatPosterImage : ImageView = view.findViewById(R.id.chatPosterImage)
    }
}