package com.example.scripttest

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val buttonPick = findViewById<Button>(R.id.button_pick)
        buttonPick.setOnClickListener {
            var intent = Intent()
            intent.setType("video/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Activity.RESULT_OK && data!=null){
            if(requestCode == 101){
                var uri: Uri? = data.data
                var selectedImage: String? = uri?.let { getPath(it) }
                if(selectedImage != null){
                    videoView.setVideoPath(selectedImage)
                    var mediaController: MediaController = MediaController(this)
                    videoView.setMediaController(mediaController)
                    videoView.start()
                }
            }
        }
    }

    private fun getPath(uri: Uri): String {
        var projectionArray = arrayOf(MediaStore.Video.Media.DATA)
        //var cursor: Cursor = applicationContext.contentResolver.query(uri, projectionArray, null, null,null)
        var cursor: Cursor? = applicationContext.contentResolver.query(uri, projectionArray,null,null,null)
        if(cursor != null){
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }else{
            return ""
        }
    }
}
