package com.example.radioapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.Model.util.service.ExampleService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.google.common.collect.ImmutableList


class PlayActivity : AppCompatActivity() , Player.Listener {

 companion object {

        private const val TAG = "MainActivity"
    }
    var programsList: ArrayList<ListRadio.RadioChannel> = ArrayList()
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var progressBar: ProgressBar
    lateinit var rigion:TextView
    var links:String= ""
    var link2:String=" "
    var t:String =" "
    var rig:String=" "
    var boolean:Boolean =false
    lateinit var title:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val serviceIntent = Intent(this, ExampleService::class.java)
        serviceIntent.putExtra("inputExtra", "notification running")

        ContextCompat.startForegroundService(this, serviceIntent)

        play()

    }

    private fun play() {
        title = findViewById(R.id.fm1)
        rigion=findViewById(R.id.song_name)
        links = intent.getStringExtra("link").toString()
//        val bundle = intent.extras
//        if (bundle != null) {
//            programsList = bundle.getSerializable("list") as ArrayList<ListRadio.RadioChannel>
//        }

         Log.e("tag1211","link"+programsList)


        link2= intent.getStringExtra("link2").toString()

        Log.e("tag121","link"+link2)

        t = intent.getStringExtra("title").toString()
        rig=intent.getStringExtra("rig").toString()
        if(rig.isNullOrEmpty()) {

            rigion.text ="Music entertainment"

        }else{
            rigion.text = rig

        }

        title.text =t
        boolean = intent.getBooleanExtra("bool" ,false)

        Log.e("tag12", " "+links)

        progressBar = findViewById(R.id.progressBar)

        //titleTv = findViewById(R.id.title)
        setupPlayer()

          if(boolean) {
              addMP3()
              addMP4Files()
          }



        // restore playstate on Rotation
//        if (savedInstanceState != null) {
//            if (savedInstanceState.getInt("mediaItem") != 0) {
//                val restoredMediaItem = savedInstanceState.getInt("mediaItem")
//                val seekTime = savedInstanceState.getLong("SeekTime")
//                player.seekTo(restoredMediaItem, seekTime)
//                player.play()
//            }
//        }

    }


    private fun addMP4Files() {
            val mediaItem = MediaItem.fromUri("links2")
          //  val mediaItem2 = MediaItem.fromUri(getString(R.string.myTestMp4))
            val newItems: List<MediaItem> = ImmutableList.of(mediaItem)
            player.addMediaItems(newItems)
            player.prepare()
        }

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
    private fun addMP32() {
        val mediaItem = MediaItem.fromUri(link2)
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
          addMP4Files()

//            if (playerServiceIsBound) {
//                bindPlayer()
//            }


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



    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service is ExampleService.VideoServiceBinder) {
                print("service audio service player set")
                playerView.player = service.getExoPlayerInstance()
            }
        }
    }








    }





