package com.example.temax

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temax.adapters.ChannelAdapter
import com.example.temax.manager.Message
import com.example.temax.manager.SocketManager
import org.json.JSONArray
import org.json.JSONObject

class ChatChannels : AppCompatActivity(), SocketManager.MessageListener {
    val channelsRecyclerView: RecyclerView by lazy { findViewById(R.id.channelsRecyclerView) }


    private val socketManager = SocketManager(this)
    private val channelList = mutableListOf<String>() // Populate this with user channels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_channels)

        val layoutManager = LinearLayoutManager(this)
        val adapter = ChannelAdapter(channelList)

        channelsRecyclerView.layoutManager = layoutManager
        channelsRecyclerView.adapter = adapter

        // Connect to Socket.IO server
        socketManager.connectSocket()

        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        getUserChannels(userID.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        // Disconnect the Socket.IO connection when the activity is destroyed
        socketManager.disconnectSocket()
    }

    private fun getUserChannels(userID: String) {
        val requestData = JSONObject().apply {
            put("userID", userID)
        }


        socketManager.sendRequest(requestData)
    }

    override fun onNewMessage(message: Message) {
        // Handle onNewMessage in ChatChannels
    }

    override fun userChannelsListener(data: String) {
        // Handle userChannelsListener in ChatChannels
        runOnUiThread {
            // Clear the existing list
            channelList.clear()

            // Parse the received data and add channels to the list
            val jsonArray = JSONArray(data)
            for (i in 0 until jsonArray.length()) {
                val channel = jsonArray.getString(i)
                channelList.add(channel)
            }

            // Notify the adapter that the data set has changed
            (channelsRecyclerView.adapter as? ChannelAdapter)?.notifyDataSetChanged()
        }
    }

    override fun onOtherEvent(data: String) {
        // Handle onOtherEvent in ChatChannels
    }
    override fun getChannelMessagesListener(messages: List<Message>) {
        // Handle onOtherEvent in ChatChannels
    }

}