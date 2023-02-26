/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.network

import androidx.lifecycle.asLiveData
import com.legacy.android.ServerPreference
import com.legacy.android.util.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val service: ServerService,
    private val networkService: NetworkService, private val pref: ServerPreference
) {

    fun getServerList() = networkBoundResource(
            saveCallResult = {
                pref.saveServers(it.list)
            },
            shouldFetch = { true },
            fetch = { service.getServers() },
            loadFromDb = { pref.serverList.asLiveData() }
        )

    suspend fun getIpInfo() = networkService.getIpInfo()


    suspend fun saveServer(server: ProxyServer) {
        pref.saveProxyServer(server)
    }
    suspend fun saveServerConfig(config: String) {
        pref.saveServerConfig(config)
    }
    suspend fun getCurrentConfig() = pref.serverConfig()

    val currentServer = pref.SERVER
}