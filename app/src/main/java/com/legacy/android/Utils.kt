/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log


fun getRandomPort(from: Int = 10000, to: Int = 60000): Int {
    return ((Math.random() * (to - from)).toInt()) + from
}

fun getAlphaNumericString(length: Int = 4): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun Context.setAlarm(triggerTime: Long) {
    Log.d("TEST", "setAlarm: ")
    val startIntent = Intent(this, UpdateReceiver::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(this, 1, startIntent, PendingIntent.FLAG_IMMUTABLE)
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)
}