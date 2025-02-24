package no.brauterfallet.myapplication.datasources

import no.brauterfallet.myapplication.dto.GeocoderFeature

interface GeocoderDataSource {
    suspend fun getClosestFeature(latitude: Double, longitude: Double): Result<GeocoderFeature>
    suspend fun getFeaturesByTextQuery(query: String): Result<List<GeocoderFeature>>
}