/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class UpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TEST", "onReceive: ")
        context.startService(
            Intent(
                context,
                NotificationService::class.java
            ).apply { action = ACTION_ROTATE })

        val pref = ServerPreference.getInstance(context)
        val interval = runBlocking { pref.interval.first() }
        interval?.let {
            if (it > 0) {
                context.setAlarm(SystemClock.elapsedRealtime() + (it * 60_000))
            }
        }

    }
}