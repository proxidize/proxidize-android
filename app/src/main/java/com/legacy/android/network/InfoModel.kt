/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.network

import com.google.gson.annotations.SerializedName

data class InfoModel(
    @SerializedName("ip") val IP: String,
    @SerializedName("continent_code") val continentCode: String?,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    var isDummy: Boolean = false
)

fun dummy() = InfoModel("",  "", 0.0, 0.0, true)