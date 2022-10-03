package com.example.radioapp

import android.accessibilityservice.GestureDescription
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.common.collect.ImmutableList


class PlayActivity : AppCompatActivity() , Player.Listener {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var progressBar: ProgressBar
    private lateinit var titleTv: TextView
    var links:String = ""
    var links2:String =" "
    var boolean:Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

         links = intent.getStringExtra("link").toString()
         links2 = intent.getStringExtra("link2").toString()
         boolean = intent.getBooleanExtra("bool" ,false)


        Log.e("tag12", " "+links)

        progressBar = findViewById(R.id.progressBar)

            //titleTv = findViewById(R.id.title)
        setupPlayer()



        if(boolean) {
          addMP3()
        } else{
            addMP32()

        }



  //      addMP4Files()


            // restore playstate on Rotation
            if (savedInstanceState != null) {
                if (savedInstanceState.getInt("mediaItem") != 0) {
                    val restoredMediaItem = savedInstanceState.getInt("mediaItem")
                    val seekTime = savedInstanceState.getLong("SeekTime")
                    player.seekTo(restoredMediaItem, seekTime)
                    player.play()
                }
            }
        }

    private fun addMP32() {
        val mediaItem = MediaItem.fromUri(links2)
        player.setMediaItem(mediaItem)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
    }


//        private fun addMP4Files() {
//            val mediaItem = MediaItem.fromUri("links")
//          //  val mediaItem2 = MediaItem.fromUri(getString(R.string.myTestMp4))
//            val newItems: List<MediaItem> = ImmutableList.of(mediaItem)
//            player.addMediaItems(newItems)
//            player.prepare()
//        }

        private fun setupPlayer() {
            player = ExoPlayer.Builder(this).build()
            playerView = findViewById(R.id.video_view)
            playerView.player = player
            player.addListener(this)
        }

        private fun addMP3() {

            val mediaItem = MediaItem.fromUri(links)
            player.setMediaItem(mediaItem)
            // Set the media item to be played.
            player.setMediaItem(mediaItem)
            // Prepare the player.
            player.prepare()
        }


        override fun onStop() {
            super.onStop()
            player.release()
        }

        override fun onResume() {
            super.onResume()
            setupPlayer()
            addMP3()
        //    addMP4Files()
        }

        // handle loading
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_BUFFERING -> {
                    progressBar.visibility = View.VISIBLE

                }
                Player.STATE_READY -> {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }

        //get Title from metadata
        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {

       //     titleTv.text = mediaMetadata.title ?: mediaMetadata.displayTitle ?: "no title found"

        }

        // save details if Activity is destroyed
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            Log.d(TAG, "onSaveInstanceState: " + player.currentPosition)

            outState.putLong("SeekTime", player.currentPosition)
            // current mediaItem
            outState.putInt("mediaItem", player.currentMediaItemIndex)
        }

        override fun onDestroy() {
            super.onDestroy()
            Log.d(TAG, "onSaveInstanceState: " + player.currentPosition)
        }

        companion object {
            private const val TAG = "MainActivity"
        }
    }





