/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import com.legacy.android.MyApplication
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.res.AssetManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.logging.FileHandler
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        createnotficationchannel()
        copyToSD("config.ini")
        LogFileManager.getInstance().createLogFile(this)
        val dbHelper = CredentialsDatabase(this)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("hostIp","0.0.0.0")
            put("region","Create Your Own")
            put("port",1000)
            put("token","00000000")
            put("latitude",0.0)
            put("longitude",0.0)
        }
       db.insert("mycredentials", null, values)
    }

    private fun createnotficationchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                channel_1_id, "channel 1", NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "give the user some important information about the connection"
            channel1.enableLights(true)
            channel1.enableVibration(true)
            val servicechannel = NotificationChannel(
                channel_2_id, "channel 1", NotificationManager.IMPORTANCE_DEFAULT
            )
            servicechannel.description =
                "this channel is for a foreground service to let the user aware " +
                        "when he is connected to the proxy and that the application in working background"
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(servicechannel)
        }
    }

    private fun copyToSD(dbName: String) {
        var `in`: InputStream? = null
        var out: FileOutputStream? = null
        val file = File(this.filesDir, dbName)
        if (file.exists()) {
            file.delete()
        }
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val assets = assets
        try {
            `in` = assets.open(dbName)
            out = FileOutputStream(file)
            val b = ByteArray(1024)
            var len = -1
            while (`in`.read(b).also { len = it } != -1) {
                out.write(b, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val channel_1_id = "channel1"
        const val channel_2_id = "servicechannel"
        var mContext: Context? = null
        var FILENAME = "config.ini"
        var LOGFILE = "connection.log"
    }
}