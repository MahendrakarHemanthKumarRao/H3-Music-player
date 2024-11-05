package com.example.h3mplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlin.system.exitProcess

class NotificationRecvier : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PREVIOUS -> Toast.makeText(
                context,
                "pervious clicked ",
                Toast.LENGTH_SHORT
            ).show()
            ApplicationClass.PLAY -> Toast.makeText(context, "play clicked ", Toast.LENGTH_SHORT)
                .show()
            ApplicationClass.NEXT -> Toast.makeText(context, "Next clicked ", Toast.LENGTH_SHORT)
                .show()
            ApplicationClass.EXIT -> exitProcess(1)
        }

    }
}