package com.example.radioapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.service.ExampleService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.google.common.collect.ImmutableList
import kotlinx.android.synthetic.main.radio_rcy_adapter.*


class PlayActivity( ) : AppCompatActivity() , Player.Listener {

    companion object {

        private const val TAG = "MainActivity"
    }
    var programsList: ArrayList<ListRecommed.Recommed> = ArrayList()
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var progressBar: ProgressBar
    lateinit var rigion:TextView
    var links:String= ""
    var link2:String=" "
    var t:String =" "
    var rig:String=" "
    var position: Int= 0
    var boolean:Boolean =false
    lateinit var title:TextView
    lateinit var backImage:ImageView
    lateinit var exoprevious:ImageView
    lateinit var exonext:ImageView
    lateinit var layout: ConstraintLayout
    lateinit var imagplay:ImageView
    lateinit var imagepause:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      //  Log.d(TAG, "programlistRadio " + programsList)


        val serviceIntent = Intent(this, ExampleService::class.java)
        serviceIntent.putExtra("inputExtra", "notification running")

        ContextCompat.startForegroundService(this, serviceIntent)

        play()

     backImage.setOnClickListener {
         finish()
     }

        exoprevious.setOnClickListener{

            nextPrevVideo(isNext = false)

        }
      exonext.setOnClickListener {

          nextPrevVideo()

      }

//        layout.setOnClickListener {
//
//            if(player.isPlaying) {
//                player.stop()
//
//            }
//             else {
//                player.play()
//
//            }
//
//        }



    }

    private fun play() {
        imagplay =findViewById(R.id.exo_play)
        imagepause=findViewById(R.id.exo_pause)



        layout = findViewById(R.id.layout1)
        exonext = findViewById(R.id.exo_nextt)
        exoprevious = findViewById(R.id.exo_prevv)
        title = findViewById(R.id.fm1)
        backImage = findViewById(R.id.img_left)
        rigion = findViewById(R.id.song_name)
        links = intent.getStringExtra("link").toString()
        programsList =
            intent.getParcelableArrayListExtra<ListRecommed.Recommed>("list") ?: ArrayList()

        Log.e("tagArray", "  " + programsList)

        position = intent.getIntExtra("po", 0)
        link2 = intent.getStringExtra("link2").toString()
        //    t = intent.getStringExtra("title").toString()
        //     rig=intent.getStringExtra("rig").toString()
        if (rig.isNullOrEmpty()) {
        //    rigion.text ="Music entertainment"

        } else {
            //   rigion.text = rig

        }

        //   title.text =t
        boolean = intent.getBooleanExtra("bool", false)

        Log.e("tag12", " " + links)

        progressBar = findViewById(R.id.progressBar)

        //titleTv = findViewById(R.id.title)


        if (boolean) {
           addMP3()
            addMP4Files()
        }
    }
    private fun addMP4Files() {

        val mediaItem = MediaItem.fromUri(" ")
           //  val mediaItem2 = MediaItem.fromUri(getString(R.string.myTestMp4))
           val newItems: List<MediaItem> = ImmutableList.of(mediaItem)
           player.addMediaItems(newItems)
           player.prepare()


    }

    private fun addMP3() {
            title.text = programsList[position].name
            rigion.text =programsList[position].genre
            player = ExoPlayer.Builder(this).build()
            playerView = findViewById(R.id.video_view)
            playerView.player = player
            player.addListener(this)

            val mediaItem = programsList[position].st_link?.let { MediaItem.fromUri(it) }
                if (mediaItem != null) {
                    player.setMediaItem(mediaItem)
                }

            // Prepare the player.
                player.prepare()

    }


    private fun nextPrevVideo(isNext:Boolean=true){
        if(isNext)
           setPostion( )
          else
              setPostion(isIncreament = false)

           addMP3()
        boolean =false
    }
    private fun setPostion(isIncreament:Boolean=true){

        if(isIncreament){
            if(programsList.size-1 == position)
                position=0
            else
                ++position

        }else{
              if(position==0)
               position==programsList.size-1

            else
                --position
        }
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
            addMP3()
          addMP4Files()
        }



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
//        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
//            title.text = mediaMetadata.title ?: mediaMetadata.displayTitle ?: "no title found"
//
//        }

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





