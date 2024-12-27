package no.nav.dagpenger.pdlcache.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import java.time.Duration

fun getEnv(propertyName: String): String? {
    return System.getProperty(propertyName, System.getenv(propertyName))
}

fun isCurrentlyRunningLocally(): Boolean {
    return getEnv("RUNNING_LOCALLY").toBoolean()
}

lateinit var httpClient: HttpClient
fun defaultHttpClient(): HttpClient {
    if (!::httpClient.isInitialized) {
        httpClient = HttpClient(CIO) {
            install(HttpTimeout) {
                connectTimeoutMillis = Duration.ofSeconds(60).toMillis()
                requestTimeoutMillis = Duration.ofSeconds(60).toMillis()
                socketTimeoutMillis = Duration.ofSeconds(60).toMillis()
            }
            expectSuccess = false
        }
    }

    return httpClient
}
