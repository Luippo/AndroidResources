package com.example.scripttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonMusicPlayer = findViewById<Button>(R.id.reproductor)
        buttonMusicPlayer.setOnClickListener { startActivity(Intent(this,KotlinMusicPlayer::class.java)) }
    }
}
