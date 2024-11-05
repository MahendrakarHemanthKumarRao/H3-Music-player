package com.example.h3mplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.h3mplayer.databinding.ActivityPlayer2Binding

class PlayerActivi : AppCompatActivity(), ServiceConnection {

    companion object {
        lateinit var musicListPa: ArrayList<Music>
        var songpostion: Int = 0
        var isplaying: Boolean = false
        var musicService: MusicService? = null
    }


    private lateinit var binding: ActivityPlayer2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_H3MPlayer)
        binding = ActivityPlayer2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //for starting service
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)
        initialzieLayout()
        binding.playpausebtn.setOnClickListener {
            if (isplaying) pausemusic()
            else playmusic()
        }
        binding.perviousbtnpa.setOnClickListener { prenextsong(increment = false) }
        binding.nextpa.setOnClickListener { prenextsong(increment = true) }

    }

    private fun setLayout() {
        Glide.with(this)
            .load(musicListPa[songpostion].arturi)
            .apply(RequestOptions().placeholder(R.drawable.musicplayericon).centerCrop())
            .into(binding.songimg)
        binding.songname.text = musicListPa[songpostion].title
    }

    private fun createMediaPlayer() {
        try {
            if (musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPa[songpostion].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isplaying = true
            binding.playpausebtn.setIconResource(R.drawable.ic_baseline_pause_24)
        } catch (e: Exception) {
            return
        }
    }

    private fun initialzieLayout() {
        songpostion = intent.getIntExtra("index", 0)
        when (intent.getStringExtra("class")) {
            "MusicAdaptor" -> {
                musicListPa = ArrayList()
                musicListPa.addAll(MainActivity.MusicListMA)
                setLayout()
            }
            "MainActivity" -> {
                musicListPa = ArrayList()
                musicListPa.addAll(MainActivity.MusicListMA)
                musicListPa.shuffle()
                setLayout()
            }

        }
    }

    private fun playmusic() {
        binding.playpausebtn.setIconResource(R.drawable.ic_baseline_pause_24)
        isplaying = true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pausemusic() {
        binding.playpausebtn.setIconResource(R.drawable.ic_baseline_play_arrow_24)
        isplaying = false
        musicService!!.mediaPlayer!!.pause()
    }

    private fun prenextsong(increment: Boolean) {
        if (increment) {
            setsongposition(increment = true)
            +songpostion
            setLayout()
            createMediaPlayer()
        } else {
            setsongposition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }

    private fun setsongposition(increment: Boolean) {
        if (increment) {
            if (musicListPa.size - 1 == songpostion)
                songpostion = 0
            else ++songpostion
        } else {
            if (0 == songpostion)
                songpostion = musicListPa.size - 1
            else --songpostion
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

}