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
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object{
        const val CHANNEL_ID = "Hallym"
        const val CHANNEL_NAME = "smart_portfolio"
    }

    // 토큰 생성
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    // 메시지 수신
    override fun onMessageReceived(message: RemoteMessage) {

        if(message.data.isNotEmpty()){
            sendNotification(message)
        }
        else{
            Log.d("testt", "onMessageReceived: data가 비어있습니다. 메시지를 수신하지 못했습니다")
        }
    }


    // 알림 생성(아이콘, 알림 소리 등)
    private fun sendNotification(remoteMessage: RemoteMessage){
        // RemoteCode, ID 를 고유값으로 지정하여 알림이 개별 표시 되도록 함
        val uniId = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)

        // 알림 채널 이름
        val channelId = getString(R.string.firebase_notification_channel_id)

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.data["body"].toString())
            .setContentText(remoteMessage.data["title"].toString())
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후부터는 채널이 필요
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(uniId, notificationBuilder.build())
    }
}