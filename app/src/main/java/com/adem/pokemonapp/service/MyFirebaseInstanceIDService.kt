package com.adem.pokemonapp.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
// Firebase notification service

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

}