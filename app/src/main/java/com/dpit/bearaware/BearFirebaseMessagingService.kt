package com.dpit.bearaware

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class BearFirebaseMessagingService : FirebaseMessagingService() {
    private val FIREMSG_TAG = "FIREBASE MESSAGING"

    override fun onNewToken(token: String) {
        Log.d(FIREMSG_TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(FIREMSG_TAG, "From: ${message.from}")

        val intent = Intent(this, AlertActivity::class.java)
            .setFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}