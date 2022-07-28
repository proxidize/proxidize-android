package com.legacy.android

data class ProxyServer(val host: String, val port: String, val token: String) {

    companion object {
        const val HOST = "138.201.246.49"
        const val SERVER_PORT = "2000"
        const val TOKEN = "12345678"

        fun default() = ProxyServer(HOST, SERVER_PORT, TOKEN)
    }
}
