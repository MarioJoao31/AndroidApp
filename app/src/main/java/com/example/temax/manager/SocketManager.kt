package com.example.temax.manager

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketManager private constructor() {
    companion object {
        private var instance: SocketManager? = null

        @Synchronized
        fun getInstance(): SocketManager {
            if (instance == null) {
                instance = SocketManager()
            }
            return instance!!
        }
    }

    private lateinit var mSocket: Socket

    init {
        try {
            mSocket = IO.socket("YOUR_SOCKET_IO_SERVER_URL")
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    fun connect() {
        mSocket.connect()
    }

    fun disconnect() {
        mSocket.disconnect()
    }
}