package me.priya.newsapp.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import me.priya.newsapp.R

class MyFireBaseMessageService : FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title ?: "News"
        val body = message.notification?.body ?: "Breaking News"

        showNotification(title, body)

    }

    private fun showNotification(title: String, body: String) {
        val notification = NotificationCompat.Builder(this , "News_channel")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }

}