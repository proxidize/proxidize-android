/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import com.legacy.android.ServerPreference
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

fun response404() = JSONObject().put("Response", "Page not found").toString()
fun responseError(status: NanoHTTPD.Response.IStatus, msg: String): NanoHTTPD.Response =
    NanoHTTPD.newFixedLengthResponse(
        status,
        "application/json",
        JSONObject().put("response", msg).toString()
    )


fun changeIp(context: Context, t: String?): NanoHTTPD.Response {
    val json = JSONObject()
    if (!isDefaultCurrentAssist(context)) {
        json.put("response", "Default assistant is required")
    } else if (t != runBlocking { ServerPreference.getInstance(context).pass.first() }) {
        json.put("response", "invalid password")
    } else {
        toggleAirPlaneMode(context)
        json.put("response", "success")
    }
    return NanoHTTPD.newFixedLengthResponse(
        NanoHTTPD.Response.Status.OK,
        "application/json",
        json.toString()
    )
}

fun isDefaultCurrentAssist(context: Context): Boolean {
    val setting = Settings.Secure.getString(context.contentResolver, "assistant")
    return if (setting != null) {
        ComponentName.unflattenFromString(setting)?.packageName == context.packageName
    } else false
}

fun toggleAirPlaneMode(context: Context) {
    if (!isDefaultCurrentAssist(context)) return
    context.startService(Intent(context, VoiceInteractionServiceImp::class.java).setAction("start"))
    Handler(Looper.getMainLooper()).postDelayed({
        context.startService(
            Intent(
                context,
                VoiceInteractionServiceImp::class.java
            ).setAction("stop")
        )
    }, 5000)
}