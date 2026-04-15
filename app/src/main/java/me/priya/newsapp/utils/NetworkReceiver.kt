package me.priya.newsapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.work.impl.utils.ForceStopRunnable

class NetworkReceiver  : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {

        if(isInternetAvailable(context)){
            Toast.makeText(context,"Internet Connected", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Internet is not Connected", Toast.LENGTH_LONG).show()

        }

    }


    private fun isInternetAvailable(context: Context) : Boolean {
        var manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var network = manager.activeNetwork

        val capabilities = manager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

}