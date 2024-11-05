package com.example.h3mplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.h3mplayer.databinding.ActivityPlaylist2Binding

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylist2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_H3MPlayer)
        binding = ActivityPlaylist2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}