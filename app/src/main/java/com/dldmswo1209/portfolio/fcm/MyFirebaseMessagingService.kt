package com.dldmswo1209.portfolio.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dldmswo1209.portfolio.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object{
        const val CHANNEL_ID = "HALLYM"
        const val CHANNEL_NAME = "SMART_PORTFOLIO_PUSH"
    }

    // 토큰 생성
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    // 메시지 수신
    override fun onMessageReceived(message: RemoteMessage) {
        val sharedPreferences = getSharedPreferences("isChatting", Context.MODE_PRIVATE)
        val isChatting = sharedPreferences.getBoolean("isChatting", false)

        if(message.data.isNotEmpty() && !isChatting){
            sendNotification(message)
        }
    }


    // 알림 생성(아이콘, 알림 소리 등)
    private fun sendNotification(remoteMessage: RemoteMessage){
        val id = System.currentTimeMillis().toInt()

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        val builder: NotificationCompat.Builder

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(notificationManager.getNotificationChannel(CHANNEL_ID) == null){
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        }else{
            builder = NotificationCompat.Builder(applicationContext)
        }

        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notification = builder.build()
        notificationManager.notify(id, notification)

    }
}