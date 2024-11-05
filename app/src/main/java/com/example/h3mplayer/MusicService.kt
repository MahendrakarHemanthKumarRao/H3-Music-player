package com.example.h3mplayer

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.h3mplayer.R.drawable.music__1_

class MusicService : Service() {
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat


    override fun onBind(intent: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext, "H3 Player")
        return myBinder
    }

    inner class MyBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }

    fun showNotification() {

        val preIntent = Intent(
            baseContext,
            NotificationRecvier::class.java
        ).setAction(ApplicationClass.PREVIOUS)
        val prePendingIntent =
            PendingIntent.getBroadcast(baseContext, 0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val playIntent =
            Intent(baseContext, NotificationRecvier::class.java).setAction(ApplicationClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            playIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val nextIntent =
            Intent(baseContext, NotificationRecvier::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val exitIntent =
            Intent(baseContext, NotificationRecvier::class.java).setAction(ApplicationClass.EXIT)
        val exitPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            exitIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivi.musicListPa[PlayerActivi.songpostion].title)
            .setContentText(PlayerActivi.musicListPa[PlayerActivi.songpostion].artist)
            .setSmallIcon(R.drawable.baseline_music_note_24)
            .setLargeIcon(BitmapFactory.decodeResource(resources, music__1_))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_baseline_skip_previous_24, "previous", prePendingIntent)
            .addAction(R.drawable.ic_baseline_play_arrow_24, "play", playPendingIntent)
            .addAction(R.drawable.ic_baseline_skip_next_24, "next", nextPendingIntent)
            .addAction(R.drawable.ic_baseline_exit_to_app_24, "Exit", exitPendingIntent)
            .build()
        startForeground(100, notification)
    }
}