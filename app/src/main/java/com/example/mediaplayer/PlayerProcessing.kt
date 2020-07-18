package com.example.mediaplayer

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player_processing.*


class PlayerProcessing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_processing)
        val intent = intent
        val bundle = intent.extras
        if (bundle !== null){
            tvSongName.text = bundle.getString("mTitle")
            tvAuthor.text = bundle.getString("mAuthor")
            sbProgressPlayer.max = bundle.getString("mSize")!!.toInt()
            buttonPlay.text = "Stop"
        }
        buttonPlay.setOnClickListener {
            if (buttonPlay.text == "Stop") {
                Service.stopPlay()
                buttonPlay.text = "Start"
            }
        }
        buttonPause.setOnClickListener {
            if (buttonPause.text == "Pause") {
                Service.pausePlay()
                buttonPause.text = "Resume"
            } else {
                Service.resumePlay()
                buttonPause.text = "Pause"
            }
        }
        sbProgressPlayer?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                var mp = Service.mp
                if(mp != null){
                    Service.seekToProgress(seek.progress)
                }
            }
        })
        var myTracking = MySongTrack()
        myTracking.start()
    }
    inner class MySongTrack(): Thread() {

        override fun run() {
            while (true) {
                try {
                    Thread.sleep(1000)
                } catch (ex: Exception) {

                }
                runOnUiThread {
                    val progress = Service.progress
                    sbProgressPlayer.progress = progress
                    Service.setSbProgress(Service.mp!!.currentPosition)
                }
            }
        }
    }
}