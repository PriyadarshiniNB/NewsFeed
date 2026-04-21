package me.priya.newsapp.ui.screens.MainScreen

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.priya.newsapp.AlarmReceiver
import java.util.Calendar

@SuppressLint("ServiceCast")
@Composable
fun MainScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        val context = LocalContext.current
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onNavigate("top_headlines") }) { Text("Top HeadLines") }
            Button(onClick = { onNavigate("sources") }) { Text("Sources") }
            Button(onClick = { onNavigate("search") }) { Text("search") }
            Button(onClick = { onNavigate("countriesList") }) { Text("Countries") }
            Button(onClick = { onNavigate("languageList") }) { Text("Language") }
            Button(onClick = { onNavigate("offline") }) { Text("offline") }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    setSimpleAlarm(context, alarmManager)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Please click if you want to set alarm for you daily to check fresh news")
            }
        }
    }
}

fun setSimpleAlarm(context: Context, alarmManager: AlarmManager?) {
    val intent = Intent(context, AlarmReceiver::class.java)

    //PendingIntent is a message you want to send later, even if your app is closed.
    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    //flag update current - It prevents your app from accidentally creating 100 identical alarms if the user clicks the button 100 times.
    //FLAG_IMMUTABLE - Once I give you this envelope, nobody (not even the System or another app) can change what is written inside."

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)

        // If it's already past 10 AM today, schedule for tomorrow
        if (before(Calendar.getInstance())) {
            add(Calendar.DATE, 1)
        }
    }

    //  THE SECURITY CHECK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager?.canScheduleExactAlarms() == false) {
            // The user hasn't granted permission in Settings
            val intentSettings = Intent().apply {
                action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            }
            context.startActivity(intentSettings)
            Toast.makeText(context, "Please allow Exact Alarms in Settings", Toast.LENGTH_LONG)
                .show()
            return
        }
    }

    // If we have permission, set the alarm
    alarmManager?.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )

    Toast.makeText(context, "Alarm set At exact 10 AM Everyday", Toast.LENGTH_SHORT).show()
}