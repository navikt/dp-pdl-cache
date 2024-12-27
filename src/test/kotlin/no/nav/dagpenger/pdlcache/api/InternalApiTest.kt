package no.nav.dagpenger.pdlcache.api

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlin.test.Test
import kotlin.test.assertEquals

class InternalApiTest : TestBase() {

    @Test
    fun testInternalApi() = setUpTestApplication {
        var response = client.get("/internal/isalive")
        assertEquals(HttpStatusCode.OK, response.status)

        response = client.get("/internal/isready")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
