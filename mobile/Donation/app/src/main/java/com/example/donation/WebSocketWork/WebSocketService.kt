package com.example.bloodsewa

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService

class WebSocketService : LifecycleService() {
    private val serverUrl = "wss://your.websocket.server.url" // Replace with your WebSocket server URL
    private val webSocketManager = WebSocketManager(this, serverUrl)

    override fun onCreate() {
        super.onCreate()

        // Create notification channel
        createNotificationChannel()

        // Start WebSocket connection
        webSocketManager.connect { message ->
            Log.d("WebSocket", "Received: $message")
            // Handle received WebSocket message (e.g., show notification)
            showNotification(message)
        }

        // Start service as a foreground service
        val notification = createNotification("WebSocket service running...")
        startForeground(1, notification)
    }
//
//     fun onBind(intent: Intent?): IBinder? {
//        return intent?.let { super.onBind(it) }
//    }

    private fun createNotification(content: String): Notification {
        return NotificationCompat.Builder(this, "websocket_channel")
            .setContentTitle("WebSocket Service")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .build()
    }

    private fun showNotification(message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(this, "websocket_channel")
            .setContentTitle("New Blood Request")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WebSocket Notifications"
            val descriptionText = "Notifications for WebSocket messages"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("websocket_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
