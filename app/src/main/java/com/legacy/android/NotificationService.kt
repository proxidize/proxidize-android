/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.legacy.android.service.ProxidizeServer
import com.legacy.android.service.changeIp
import com.legacy.android.service.toggleAirPlaneMode
import com.legacy.android.ui.ui.home.HomeFragment.Companion.NOTIFICATION_ID
import fi.iki.elonen.NanoHTTPD
import java.io.IOException

const val ACTION_CLOSE = "CLOSE_SERVICE"
const val ACTION_ROTATE = "ROTATE_IP"

class NotificationService : Service() {


    private val server by lazy { ProxidizeServer(this) }

    private val wakeLock: PowerManager.WakeLock by lazy {
        (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag")
        }
    }

    @SuppressLint("WakelockTimeout")
    override fun onCreate() {
        super.onCreate()
        wakeLock.acquire()

        try {
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
        } catch (e: IOException) {
            Log.d("onCreate: ", "IOException", e)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (ACTION_CLOSE == intent.action) {
            stopSelf()
            System.exit(0)
            return START_NOT_STICKY
        } else if (ACTION_ROTATE == intent.action) {
            toggleAirPlaneMode(this)
            return START_NOT_STICKY
        }
        val intent1 = Intent(this, NotificationService::class.java)
        intent1.action = ACTION_CLOSE
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
        startForeground(NOTIFICATION_ID, notification)
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