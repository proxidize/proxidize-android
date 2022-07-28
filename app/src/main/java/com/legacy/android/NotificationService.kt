package com.legacy.android

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat

class NotificationService : Service() {

    private val wakeLock: PowerManager.WakeLock by lazy {
        (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag")
        }
    }

    @SuppressLint("WakelockTimeout")
    override fun onCreate() {
        super.onCreate()
        wakeLock.acquire()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if ("close_service" == intent.action) {
            stopSelf()
            System.exit(0)
            return START_NOT_STICKY
        }
        val intent1 = Intent(this, NotificationService::class.java)
        intent1.action = "close_service"
        var pIntent: PendingIntent? = null
        pIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getService(this, 0, intent1, PendingIntent.FLAG_MUTABLE)
        } else PendingIntent.getService(this, 0, intent1, 0)
        val stat = intent.getStringExtra("stat")
        val notification =
            NotificationCompat.Builder(applicationContext, MyApplication.channel_2_id)
                .setContentTitle("Service working")
                .setContentText(stat)
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.ic_baseline_close_24,
                        "Close",
                        pIntent
                    )
                )
                .setSmallIcon(R.drawable.ic_baseline_miscellaneous_services_24)
                .build()
        startForeground(LoginActivity.NOTIFICATION_ID, notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (wakeLock.isHeld) {
            wakeLock.release()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}