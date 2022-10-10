package com.example.radioapp.service

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.radioapp.app.RApp.Companion.CHANNEL_ID
import com.example.radioapp.PlayActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerView


class ExampleService: Service() , Player.Listener{

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView


    override fun onBind(intent: Intent?): IBinder? {
            return VideoServiceBinder()
        }


    private lateinit var playerNotificationManager: PlayerNotificationManager
    private lateinit var playerNotification: PlayerNotificationManager.Builder



    inner class VideoServiceBinder : Binder() {
        fun getExoPlayerInstance() = player
    }
    override fun onCreate() {
        super.onCreate()


        player = SimpleExoPlayer.Builder(this).build()
        player.addListener(this)


        // define notification behavior

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")

        addMP3()

        val notificationIntent = Intent(this,PlayActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_delete)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)


        return START_NOT_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    private fun setupPlayer() {
//        player = ExoPlayer.Builder(this).build()
//        playerView = findViewById(R.id.video_view)
//        playerView.player = player
//        player.addListener(this)
    }

    private fun addMP3() {

        val mediaItem = MediaItem.fromUri("https://media.radiomirchi.com/audios/news_content/17-1512117407.mp3")
        player.setMediaItem(mediaItem)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
    }

}