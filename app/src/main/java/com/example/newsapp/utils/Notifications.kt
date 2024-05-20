package com.example.newsapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.newsapp.R

object Notifications {
    private const val CHANNEL_ID = "MyAppChannelId"
    private const val CHANNEL_NAME = "MyAppChannel"

    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Cria o canal de notificação (necessário para dispositivos com API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Constrói a notificação
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_notifications_24)
        // Adicione mais configurações conforme necessário

        // Exibe a notificação
        notificationManager.notify(1, builder.build())
    }
}