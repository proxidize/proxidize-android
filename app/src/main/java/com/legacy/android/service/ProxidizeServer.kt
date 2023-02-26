/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.service


import android.content.Context
import fi.iki.elonen.NanoHTTPD
import java.util.logging.Logger

/**
 * @param context
 * */
class ProxidizeServer(private val context: Context) : NanoHTTPD(8080) {
    override fun serve(session: IHTTPSession): Response {
        val method = session.method
        val uri = session.uri
        LOG.info("$method '$uri'  ")
        return when (method) {
            Method.GET -> handleGetRequest(session)
            else -> newFixedLengthResponse(
                Response.Status.NOT_FOUND,
                "application/json",
                response404()
            )
        }


    }

    /**
     * @param session
     * @return
     * */
    private fun handleGetRequest(session: IHTTPSession): Response {
        return when (session.uri) {
            "/change_ip" -> changeIp(context, session.parameters["t"]?.get(0).toString())
            else -> newFixedLengthResponse(
                Response.Status.NOT_FOUND,
                "application/json",
                response404()
            )
        }
    }

    companion object {
        val LOG: Logger = Logger.getLogger(ProxidizeServer::class.java.name)
    }
}