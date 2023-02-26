/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.network


import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET


interface NetworkService {

    @GET("json")
    suspend fun getIpInfo(): NResponse<InfoModel>

    companion object {
        const val HOST_NAME_ = "https://ipapi.co/"
    }
}
typealias NResponse<T> = NetworkResponse<T, Any>