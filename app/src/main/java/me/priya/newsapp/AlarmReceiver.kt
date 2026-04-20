package me.priya.newsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        Toast.makeText(context, "Alarm Triggered! Time to check news!", Toast.LENGTH_LONG).show()
    }
}