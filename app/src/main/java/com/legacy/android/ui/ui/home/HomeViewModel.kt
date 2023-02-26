/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.ui.ui.home


import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponse
import com.legacy.android.network.InfoModel
import com.legacy.android.network.NetworkRepository
import com.legacy.android.network.ProxyServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val triger = MutableLiveData(true)
    val servers = triger.switchMap { repository.getServerList() }

    val currentServer = repository.currentServer.asLiveData()

    suspend fun getCurrentConfig() = repository.getCurrentConfig()


    fun calculateServer(serverList: List<ProxyServer>) = viewModelScope.launch(Dispatchers.IO) {
        if (serverList.isEmpty())
            return@launch
        when (val ipInfo = repository.getIpInfo()) {
            is NetworkResponse.Success -> calculateDistance(ipInfo.body, serverList)
            else -> selectRandom(serverList)
        }
    }

    private suspend fun calculateDistance(infoModel: InfoModel, serverList: List<ProxyServer>) {
        Log.d("Server", "calculateDistance: ")
        if (serverList.isEmpty()) return
        val serverIndex = serverList.withIndex().minByOrNull { (_, s) ->
            distance(
                infoModel.latitude,
                infoModel.longitude,
                s.latitude,
                s.longitude
            )
        }?.index
        Log.d("Server", "calculateDistance: index $serverIndex")
        val server = serverList[serverIndex ?: 0]
        repository.saveServerConfig("Auto")
        repository.saveServer(server)


    }

    private fun selectRandom(serverList: List<ProxyServer>) {
        Log.d("Server", "selectRandom: ")
        if (serverList.isEmpty()) return

        viewModelScope.launch {
            repository.saveServerConfig("Auto")
            repository.saveServer(serverList.random())
        }

    }

    fun selectedServer(proxyServer: ProxyServer) {
        viewModelScope.launch {
            repository.saveServer(proxyServer)
        }
    }

    fun serverConfig(config: String) {
        viewModelScope.launch {
            repository.saveServerConfig(config)
        }
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}