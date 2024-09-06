package com.dpit.bearaware

import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel() {
    private val client = HttpClient(OkHttp) {
        install(WebSockets)
        engine {
            preconfigured = OkHttpClient.Builder()
                .pingInterval(20, TimeUnit.SECONDS)
                .build()
        }
    }

    init {

        /*viewModelScope.launch {
            client.webSocket(
                method = HttpMethod.Get,
                host = "192.168.100.99",
                port = 8080,
                path = "/chat"
            ) {
                while (true) {
                    val othersMessage = incoming.receive() as? Frame.Text ?: continue
                    println(othersMessage.readText())
                }
            }
        }*/
    }
}