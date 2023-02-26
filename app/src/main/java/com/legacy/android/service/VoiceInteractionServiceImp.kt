/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.service

import android.app.Service


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.voice.VoiceInteractionService
import android.service.voice.VoiceInteractionSession

class VoiceInteractionServiceImp : VoiceInteractionService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            if (intent?.action == "start")
                showSession(Bundle().apply {
                    putBoolean(
                        Settings.EXTRA_AIRPLANE_MODE_ENABLED,
                        true
                    )
                }, VoiceInteractionSession.SHOW_WITH_SCREENSHOT)
            else if (intent?.action == "stop")
                showSession(Bundle().apply {
                    putBoolean(
                        Settings.EXTRA_AIRPLANE_MODE_ENABLED,
                        false
                    )
                }, VoiceInteractionSession.SHOW_WITH_SCREENSHOT)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        stopSelf()

        return START_NOT_STICKY

    }

}
