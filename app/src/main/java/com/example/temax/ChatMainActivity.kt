package com.example.temax

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temax.adapters.MessageAdapter
import com.example.temax.manager.Message
import com.example.temax.manager.SocketManager
import org.json.JSONException
import org.json.JSONObject

class ChatMainActivity : AppCompatActivity() , SocketManager.MessageListener {
    val etMessage: EditText by lazy { findViewById(R.id.editTextMessage) }
    //call socket

    private val socketManager = SocketManager(this)
    private val messageList = mutableListOf<Message>()
    var channelID: String = ""

    //recicler view
    val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerViewChat) }
    val adapter = MessageAdapter(messageList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)


        //lista de mensagens
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        //conenct em todas as activites
        socketManager.connectSocket()

        channelID = intent.getStringExtra("channelID").toString()

        val data = JSONObject().apply {
            put("channelID", channelID)
        }

        //get channel messages
        socketManager.sendRequest2(data)
        //join channel
        socketManager.sendRequest4(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Disconnect the Socket.IO connection when the activity is destroyed
        socketManager.disconnectSocket()
    }

    //func to send message
    fun SendMessage(view: View) {

        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        val messageText = etMessage.text.toString()

        val data = JSONObject().apply {
            put("channelID", channelID).put("name",userID.toString()).put("text",messageText)
        }

        socketManager.sendRequest3(data)
        etMessage.text.clear()

    }

    override fun getChannelMessagesListener(messages: List<Message>) {
        runOnUiThread {
            try {
                Log.d("sockerAPI","mensagem todas do channel$messages")
                // Clear the existing list
                messageList.clear()

                // Add the new messages to the list
                messageList.addAll(messages)
                // Notify the adapter of the new item
                adapter.notifyItemInserted(messageList.size - 1)
                adapter.notifyDataSetChanged()

            } catch (e: JSONException) {
                // Handle JSON parsing error
                e.printStackTrace()
            }
        }
    }

    // Implement the onNewMessage method to handle "new message" events
    override fun onNewMessage(message: Message) {
        // Handle the new message here
        // Update UI or perform any other action with the received data
        runOnUiThread {
            try {
                messageList.add(message)
                // Notify the adapter of the new item
                adapter.notifyItemInserted(messageList.size - 1)
                adapter.notifyDataSetChanged()

            } catch (e: JSONException) {
                // Handle JSON parsing error
                e.printStackTrace()
            }
        }
    }

    // Implement the onOtherEvent method to handle "other event" events
    override fun userChannelsListener(data: String) {

    }

    // Implement the onOtherEvent method to handle "other event" events
    override fun onOtherEvent(data: String) {
        // Handle the other event here
        // Update UI or perform any other action with the received data
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show()
    }


}
