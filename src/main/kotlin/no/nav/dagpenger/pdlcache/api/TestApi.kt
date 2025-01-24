package no.nav.dagpenger.pdlcache.api

import glide.api.GlideClient
import glide.api.models.GlideString.gs
import glide.api.models.configuration.GlideClientConfiguration
import glide.api.models.configuration.NodeAddress
import glide.api.models.configuration.ServerCredentials
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import mu.KotlinLogging
import no.nav.dagpenger.pdlcache.utils.getEnv

private val logger = KotlinLogging.logger {}

fun Routing.testApi() {
    route("/test") {
        get("/{ident}") {
            val useSsl = true

            val config =
                GlideClientConfiguration.builder()
                    .address(
                        NodeAddress.builder()
                            .host(getEnv("VALKEY_HOST_IDENTER") ?: "")
                            .port((getEnv("VALKEY_PORT_IDENTER") ?: "0").toInt())
                            .build()
                    )
                    .credentials(
                        ServerCredentials.builder()
                            .username(getEnv("VALKEY_USERNAME_IDENTER"))
                            .password(getEnv("VALKEY_PASSWORD_IDENTER") ?: "")
                            .build()
                    )
                    .useTLS(useSsl)
                    .build()

            try {
                GlideClient.createClient(config).get().use { client ->
                    logger.info("SET(apples, oranges): " + client.set(gs("apples"), gs("oranges")).get())
                    logger.info("GET(apples): " + client.get(gs("apples")).get())
                }
            } catch (e: Exception) {
                logger.error(e) { "Glide example failed with an exception: " }
            }

            call.respondText("Test")
        }
    }
}
