/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.legacy.android.network.ProxyServer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type


class ServerPreference private constructor(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")


    val SERVER: Flow<ProxyServer> = context.dataStore.data.map(::retrieveServer)
    val serverList: Flow<List<ProxyServer>> = context.dataStore.data.map(::retrieveServerList)
    val interval: Flow<Int?> = context.dataStore.data.map(::retrieveInterval)
    val pass: Flow<String> = context.dataStore.data.map(::retrievePass)


    private fun retrieveServer(pref: Preferences): ProxyServer {
        if (pref[stringPreferencesKey("host")].isNullOrBlank()) return ProxyServer.default()
        return ProxyServer(
            pref[stringPreferencesKey("host")] ?: "",
            pref[stringPreferencesKey("region")] ?: "",
            pref[intPreferencesKey("port")] ?: 2000,
            pref[stringPreferencesKey("token")],
            pref[doublePreferencesKey("latitude")] ?: 0.0,
            pref[doublePreferencesKey("longitude")] ?: 0.0
        )
    }

    private fun retrieveServerList(pref: Preferences): List<ProxyServer> {
        val serverString = pref[stringPreferencesKey("serverList")]
        Log.d("serverString", "retrieveServerList: ")
        val type: Type = object : TypeToken<List<ProxyServer?>?>() {}.type
        return Gson().fromJson(serverString, type) ?: listOf()
    }


    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun saveTimeInterval(interval: Int) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("interval")] = interval
        }
    }

    suspend fun saveUserAndPassword(userPass: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("userPass")] = userPass
        }
    }

    suspend fun savePorts(port1: Int, port2: Int, port3: Int) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("port1")] = port1
            pref[intPreferencesKey("port2")] = port2
            pref[intPreferencesKey("port3")] = port3
        }
    }

    suspend fun retrievePorts(): Triple<Int, Int, Int> {
        return Triple(
            context.dataStore.data.first()[intPreferencesKey("port1")] ?: 0,
            context.dataStore.data.first()[intPreferencesKey("port2")] ?: 0,
            context.dataStore.data.first()[intPreferencesKey("port3")] ?: 0
        )
    }


    private fun retrieveInterval(pref: Preferences): Int? {
        return pref[intPreferencesKey("interval")]
    }

    private fun retrievePass(pref: Preferences): String {
        return pref[stringPreferencesKey("userPass")] ?: ""
    }

    suspend fun saveServerConfig(config: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("selected")] = config
        }
    }

    suspend fun serverConfig(): String {
        return context.dataStore.data.first()[stringPreferencesKey("selected")] ?: "Auto"
    }

    suspend fun saveProxyServer(device: ProxyServer) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("host")] = device.IP
            pref[intPreferencesKey("port")] = device.port
            pref[doublePreferencesKey("latitude")] = device.latitude
            pref[doublePreferencesKey("longitude")] = device.longitude
            pref[stringPreferencesKey("token")] = device.token ?: ""
            pref[stringPreferencesKey("region")] = device.region
        }
    }

    suspend fun saveServers(list: List<ProxyServer>) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("serverList")] = Gson().toJson(list)
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        var INSTANCE: ServerPreference? = null
        fun getInstance(context: Context): ServerPreference {
            return INSTANCE ?: ServerPreference(context).also { INSTANCE = it }
        }
    }
}

