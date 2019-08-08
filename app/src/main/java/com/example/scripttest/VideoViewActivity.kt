package com.example.scripttest

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        val videoView = findViewById<VideoView>(R.id.video_view)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.video
        var uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)
        videoView.setMediaController(null)
        //mediaController.setAnchorView(videoView)

        val buttonPlay = findViewById<Button>(R.id.play)
        buttonPlay.setOnClickListener {
            videoView.start()
        }

        val buttonPause = findViewById<Button>(R.id.pause)
        buttonPause.setOnClickListener {
            videoView.pause()
        }

    }
}
