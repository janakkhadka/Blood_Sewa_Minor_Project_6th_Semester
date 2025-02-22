package com.example.bloodsewa


import android.util.Log
import okhttp3.*
import okio.ByteString
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.donation.R

class WebSocketManager(private val context: Context, private val serverUrl: String) {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    fun connect(onMessageReceived: (String) -> Unit) {
        val request = Request.Builder().url(serverUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "✅ Connected to WebSocket")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocket", "📩 New Message: $text")
                onMessageReceived(text)
                showNotification(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("WebSocket", "📩 Received ByteString: $bytes")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocket", "🔴 Closing: $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "⚠️ Error: ${t.message}")
            }
        })
    }

    private fun showNotification(message: String) {
        val notificationBuilder = NotificationCompat.Builder(context, "websocket_channel")
            .setSmallIcon(R.drawable.donate)
            .setContentTitle("Urgently Required Blood!!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(2, notificationBuilder.build())
    }


    fun disconnect() {
        webSocket?.close(1000, "Goodbye!")
        Log.d("WebSocket", "🔴 Disconnected")
    }
}
