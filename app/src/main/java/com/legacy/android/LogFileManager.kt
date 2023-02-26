/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import android.content.Context
import java.io.*
import javax.inject.Singleton

@Singleton
class LogFileManager private constructor() {
    private lateinit var logFile: File

    companion object {
        private var instance: LogFileManager? = null

        fun getInstance(): LogFileManager {
            if (instance == null) {
                instance = LogFileManager()
            }
            return instance!!
        }
    }

    fun createLogFile(context: Context) {
        logFile = File(context.getExternalFilesDir(null), "ProxidizeLegacyCredentialsLogs.txt")
    }

    fun writeToLogFile(message: String) {
        val bos = BufferedOutputStream(FileOutputStream(logFile,true))

        try {
            bos.write(message.toByteArray())
            bos.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            bos.close()
        }

    }

}