/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.network

import com.legacy.android.util.ApiResponse
import retrofit2.http.GET

interface ServerService {

    @GET("json")
    suspend fun getServers(): ApiResponse<ProxyServerResponse>

    companion object {
        const val HOST_NAME = "https://ipapi.co/"
    }
}