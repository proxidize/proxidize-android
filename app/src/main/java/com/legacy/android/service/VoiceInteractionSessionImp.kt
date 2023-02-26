/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.voice.VoiceInteractionSession
import android.view.View
import com.legacy.android.R


class VoiceInteractionSessionImp(context: Context) : VoiceInteractionSession(context) {

    override fun onCreateContentView(): View {
        return layoutInflater.inflate(R.layout.activity_start_service, null, false)
    }

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
        startVoiceActivity(Intent(Settings.ACTION_VOICE_CONTROL_AIRPLANE_MODE).also {
            it.putExtra(
                Settings.EXTRA_AIRPLANE_MODE_ENABLED,
                args?.getBoolean(Settings.EXTRA_AIRPLANE_MODE_ENABLED)
            )
        })

    }

    override fun onTaskStarted(intent: Intent?, taskId: Int) {
        super.onTaskStarted(intent, taskId)
    }

    override fun onTaskFinished(intent: Intent?, taskId: Int) {
        super.onTaskFinished(intent, taskId)
        hide()
    }

}
