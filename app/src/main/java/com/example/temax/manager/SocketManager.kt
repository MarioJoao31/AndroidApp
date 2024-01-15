package com.example.temax.manager

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.temax.BuildConfig
import com.example.temax.ChatChannels
import com.example.temax.ChatMainActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

data class Message(
    val channelID: String,
    val name: String,
    val text: String
)

class SocketManager(private val listener: MessageListener)  {
    private var socket: Socket? = null

    // Interface to define a callback for receiving messages
    interface MessageListener {
        fun onNewMessage(message: Message)
        fun userChannelsListener(data: String)
        fun getChannelMessagesListener(messages: List<Message>)
        fun onOtherEvent(data: String)

    }


    // Function to connect to the Socket.IO server
    fun connectSocket() {
        try {
            val options = IO.Options()
            // Set additional options if needed

            socket = IO.socket("http://${BuildConfig.API_IP}:3000", options)
            socket?.connect()

            //lista de listeners
            // Listen for "new message" events
            socket?.on("new message", onNewMessageListener)
            socket?.on("userChannels", userChannelsListener)
            socket?.on("channelMessages", getChannelMessagesListener)
            socket?.on("error", onOtherEventListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val onNewMessageListener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            try {
                val jsonObject = args[0] as JSONObject

                // Extract the 'message' object
                val messageObject = jsonObject.optJSONObject("message")

                // Extract individual fields from the 'message' object
                val channelID = if (messageObject?.has("channelID") == true) messageObject.getString("channelID") else ""
                val name = if (messageObject?.has("name") == true) messageObject.getString("name") else ""
                val text = if (messageObject?.has("text") == true) messageObject.getString("text") else ""

                val message = Message(channelID, name, text)
                listener.onNewMessage(message)
                Log.d("socketAPI","new message received: $message ")

            } catch (e: JSONException) {
                // Handle JSON parsing error
                e.printStackTrace()
            }
        }
    }


    // Listener for "userChannels" events
    private val getChannelMessagesListener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            try {
                val jsonObject = args[0] as JSONObject

                // Extract messages array from the 'messages' key
                val messagesArray = jsonObject.optJSONArray("messages")

                if (messagesArray != null && messagesArray.length() > 0) {
                    val messageList = mutableListOf<Message>()

                    // Iterate through each message in the array
                    for (i in 0 until messagesArray.length()) {
                        val messageObject = messagesArray.getJSONObject(i)

                        // Extract individual fields from the message
                        val channelID = if (messageObject.has("channelID")) messageObject.getString("channelID") else ""
                        val name = if (messageObject.has("name")) messageObject.getString("name") else ""
                        val text = if (messageObject.has("text")) messageObject.getString("text") else ""

                        // Create a Message object and add it to the list
                        val message = Message(channelID, name, text)
                        messageList.add(message)
                    }

                    // Notify the listener with the list of messages
                    listener.getChannelMessagesListener(messageList)
                    Log.d("socketAPI", "All messages: $messageList")
                }

            } catch (e: JSONException) {
                // Handle JSON parsing error
                e.printStackTrace()
            }
        }
    }


    // Listener for "userChannels" events
    private val userChannelsListener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            val data = args[0].toString()
            listener.userChannelsListener(data)
        }
    }

    // Listener for "other event" events
    private val onOtherEventListener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            val data = args[0].toString()
            listener.onOtherEvent(data)
            Log.d("socketAPI",data)
        }
    }

    fun sendRequest( data: JSONObject ) {
        socket?.emit("getUserChannels", data) // Pass callback outside the parentheses
    }

    fun sendRequest2( data: JSONObject ) {
        socket?.emit("getChannelMessages", data) // Pass callback outside the parentheses
    }

    fun sendRequest3( data: JSONObject ) {
        socket?.emit("createMessage", data) // Pass callback outside the parentheses
    }

    fun sendRequest4( data: JSONObject ) {
        socket?.emit("joinChannel", data) // Pass callback outside the parentheses
    }

    fun sendRequest5( data: JSONObject ) {
        socket?.emit("createChannel", data) // Pass callback outside the parentheses
    }

    // Function to disconnect the Socket.IO connection
    fun disconnectSocket() {
        socket?.disconnect()
        socket?.off("new message", onNewMessageListener)
        socket?.off("other event", onOtherEventListener)
    }

}