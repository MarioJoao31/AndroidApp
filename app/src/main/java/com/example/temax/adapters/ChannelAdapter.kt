package com.example.temax.adapters

// ChannelAdapter.kt

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.temax.ChatMainActivity
import com.example.temax.R

class ChannelAdapter(private val channelList: List<String>) :
    RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val channelNameTextView: TextView = view.findViewById(R.id.channelNameTextView)

        fun bind(channel: String) {
            // Bind data to views
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ChatMainActivity::class.java)
                intent.putExtra("channelID", channel)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_channel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.channelNameTextView.text = channelList[position]
        val channel = channelList[position]
        holder.bind(channel)
    }

    override fun getItemCount(): Int {
        return channelList.size
    }
}
