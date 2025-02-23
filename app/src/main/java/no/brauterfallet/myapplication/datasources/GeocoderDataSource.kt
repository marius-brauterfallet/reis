package no.brauterfallet.myapplication.datasources

import no.brauterfallet.myapplication.dto.GeocoderFeature
import no.brauterfallet.myapplication.dto.GeocoderVenue

interface GeocoderDataSource {
    suspend fun getClosestVenue(latitude: Double, longitude: Double): Result<GeocoderVenue>
    suspend fun getVenuesByTextQuery(query: String): Result<List<GeocoderVenue>>

    suspend fun getClosestFeature(latitude: Double, longitude: Double): Result<GeocoderFeature>
    suspend fun getFeaturesByTextQuery(query: String): Result<List<GeocoderFeature>>
}