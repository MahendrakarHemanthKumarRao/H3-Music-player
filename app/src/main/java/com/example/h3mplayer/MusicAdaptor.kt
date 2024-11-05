package com.example.h3mplayer


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.h3mplayer.databinding.MusicVeiwBinding

class MusicAdaptor(private val context: Context, private val musicList: ArrayList<Music>) :
    RecyclerView.Adapter<MusicAdaptor.MyHolder>() {
    class MyHolder(binding: MusicVeiwBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.songname
        val album = binding.songalbum
        val image = binding.imageMv
        val duration = binding.songduration
        val root = binding.root
    }

    override fun onBindViewHolder(holder: MusicAdaptor.MyHolder, position: Int) {
        holder.title.text = musicList[position].title
        holder.album.text = musicList[position].album
        holder.duration.text = formatDuration(musicList[position].duration)
        Glide.with(context)
            .load(musicList[position].arturi)
            .apply(RequestOptions().placeholder(R.drawable.musicplayericon).centerCrop())
            .into(holder.image)
        holder.root.setOnClickListener {
            val intent = Intent(context, PlayerActivi::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", "MusicAdaptor")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(MusicVeiwBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}