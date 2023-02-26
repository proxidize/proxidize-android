/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.network

import com.google.gson.annotations.SerializedName


data class ProxyServerResponse(@SerializedName("servers") val list:List<ProxyServer>)
data class ProxyServer(
    @SerializedName("ip") val IP: String,
    @SerializedName("region") val region: String,
    @SerializedName("port") val port: Int,
    @SerializedName("token") val token: String?,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
) {
    companion object {
        fun default() = ProxyServer(
            "0.0.0.0",
            "Create Your Own",
            1000,
            "00000000",
            0.0,
            0.0
        )
    }
}