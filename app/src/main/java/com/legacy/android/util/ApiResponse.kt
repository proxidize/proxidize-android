/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.util

import android.util.Log
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

/**
 * @param
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            Log.d("Server", "create: $error")
            val errorMessage =  if (error is IOException) "Network error" else "Something went wrong"
            return ApiErrorResponse(errorMessage )
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()

                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    val json = JSONObject(msg)
                    json.getString("message")
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

public class ApiEmptyResponse<T> : ApiResponse<T>()

public data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()

public data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()