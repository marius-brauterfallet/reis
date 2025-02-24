package no.brauterfallet.myapplication.datasources

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import no.brauterfallet.myapplication.dto.GeocoderFeature
import no.brauterfallet.myapplication.dto.GeocoderResponse

class GeocoderDataSourceImpl(private val httpClient: HttpClient) : GeocoderDataSource {
    companion object {
        const val GEOCODER_URL = "https://api.entur.io/geocoder/v1"
    }

    override suspend fun getClosestFeature(
        latitude: Double,
        longitude: Double
    ): Result<GeocoderFeature> {
        return runCatching {
            httpClient.get(GEOCODER_URL) {
                url {
                    appendPathSegments("reverse")
                    parameters.append("point.lat", latitude.toString())
                    parameters.append("point.lon", longitude.toString())
                    parameters.append("boundary.circle.radius", "10")
                    parameters.append("size", "1")
                    parameters.append("layers", "venue")
                }
            }.body<GeocoderResponse>().features.first()
        }
    }

    override suspend fun getFeaturesByTextQuery(query: String): Result<List<GeocoderFeature>> {
        return runCatching {
            httpClient.get(GEOCODER_URL) {
                url {
                    appendPathSegments("autocomplete")
                    parameters.append("text", query)
                    parameters.append("size", "10")
                    parameters.append("lang", "no")
                    parameters.append("layers", "venue")
                }
            }.body<GeocoderResponse>().features
        }
    }
}