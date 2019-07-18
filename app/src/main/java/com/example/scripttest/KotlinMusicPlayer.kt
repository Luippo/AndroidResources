package com.example.scripttest

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_kotlin_music_player.*

class KotlinMusicPlayer : AppCompatActivity() {

    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_music_player)

        val volumeBar = findViewById<SeekBar>(R.id.volumeBar)
        val positionBar = findViewById<SeekBar>(R.id.positionBar)

        mp = MediaPlayer.create(this,R.raw.song)
        mp.isLooping = true
        mp.setVolume(0.5f,0.5f)
        totalTime = mp.duration


        //Volume Bar
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                // the variables
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser){
                        var volumeNum = progress / 100.0f
                        mp.setVolume(volumeNum, volumeNum)
                    }
                }
                override fun onStartTrackingTouch(seekbar: SeekBar?) { }
                override fun onStopTrackingTouch(seekbar: SeekBar?) { }
            }
        )

        // Position Bar
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser){
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekbar: SeekBar?) { }
                override fun onStopTrackingTouch(seekbar: SeekBar?) { }
            }
        )

        // Thread
        Thread(Runnable{
            while(mp != null){
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e: InterruptedException){ }
            }
        }).start()
    }

    @SuppressLint("HandlerLeak")
    var handler = object: Handler(){
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what
            var elapsedLabel = findViewById<TextView>(R.id.elapsedTimeLabel)
            var remainingLabel = findViewById<TextView>(R.id.remainingTimeLabel)
            //Update positionBar
            positionBar.progress = currentPosition

            // Update Labels
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedLabel.text = elapsedTime


            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String{
        var timeLabel = ""
        var min = time / 1000 /  60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if(sec < 10) timeLabel += "0"

        return timeLabel
    }


    fun playBtnClick(view: View){
        val button = findViewById<Button>(R.id.btnPlay)
        if(mp.isPlaying){
            // Stop
            mp.pause()
            // change the Background to play
            button.setBackgroundResource(R.drawable.play)
        }else{
            // Start
            mp.start()
            // change the Background to pause
            button.setBackgroundResource(R.drawable.stop)
        }
    }
}
