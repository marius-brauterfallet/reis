package no.brauterfallet.myapplication

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import no.brauterfallet.myapplication.datasources.GeocoderDataSource
import no.brauterfallet.myapplication.datasources.GeocoderDataSourceImpl
import org.junit.Test

class GeocoderDataSourceTests {
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    val geoCoderDataSource: GeocoderDataSource = GeocoderDataSourceImpl(httpClient)

    @Test
    fun testSomething() {
        runBlocking {
            geoCoderDataSource.getClosestVenue(60.20932986020418f, 11.55909560822487f)
        }
    }
}