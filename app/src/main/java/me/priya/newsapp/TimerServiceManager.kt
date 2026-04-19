/*package me.priya.newsapp


import android.content.*
import android.os.IBinder
import com.example.common.ITimerAidl

class TimerServiceManager(private val context: Context) {

    private var timerService: ITimerAidl? = null
    private var isBound = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerService = ITimerAidl.Stub.asInterface(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timerService = null
            isBound = false
        }
    }

    fun bindService() {
        val intent = Intent("com.example.TIMER_SERVICE")
        intent.setPackage("com.example.musicandtimerapp")

        //Directly go to MusicTimer app → find service → bind

        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService() {
        if (isBound) {
            context.unbindService(connection)
            isBound = false
        }
    }

    fun startTimer() {
        timerService?.startTimer()
    }

    fun stopTimer() {
        timerService?.stopTimer()
    }

    fun getTime(): Int {
        return timerService?.getTime() ?: 0
    }
} /*


// in compose we can call like this


//val timerManager = remember { TimerServiceManager(context) }
//
//var time by remember { mutableStateOf(0) }
//
////  Bind when screen opens
//LaunchedEffect(Unit) {
//    timerManager.bindService()
//    timerManager.startTimer()
//
//    while (true) {
//        time = timerManager.getTime()
//        delay(1000)
//    }
//}
//
////  Unbind when screen closes
//DisposableEffect(Unit) {
//    onDispose {
//        timerManager.stopTimer()
//        timerManager.unbindService()
//    }
//}
}
 */