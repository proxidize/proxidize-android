package com.legacy.android


import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServerPreference private constructor(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")


    val SERVER: Flow<ProxyServer> = context.dataStore.data.map(::retrieveServer)


    private fun retrieveServer(pref: Preferences): ProxyServer {
        if (pref[stringPreferencesKey("host")].isNullOrBlank()) return ProxyServer.default()
        return ProxyServer(
            pref[stringPreferencesKey("host")] ?: "",
            pref[stringPreferencesKey("port")] ?: "", pref[stringPreferencesKey("token")] ?: ""
        )
    }


    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun saveProxyServer(device: ProxyServer) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("host")] = device.host
            pref[stringPreferencesKey("port")] = device.port
            pref[stringPreferencesKey("token")] = device.token
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

