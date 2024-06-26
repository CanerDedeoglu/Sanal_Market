package com.canerdedeoglu.afinal.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("new token", token)
    }
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("Message Received", "${message.from} ")
        Log.d("Message messageId", "${message.messageId} ")
        Log.d("Message data", "${message.data} ")
        if (message.notification != null) {
            Log.d("Message Notification Body", "${message.notification?.body} ")
        }
    }


}