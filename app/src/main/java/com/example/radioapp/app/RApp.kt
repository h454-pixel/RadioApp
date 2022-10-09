package com.example.radioapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RApp : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Example Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager =
            getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    companion object{
        const val CHANNEL_ID = "exampleServiceChannel"
    }


}