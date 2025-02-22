package no.brauterfallet.myapplication.datasources

import no.brauterfallet.myapplication.dto.Venue

interface GeocoderDataSource {
    suspend fun getClosestVenue(latitude: Float, longitude: Float): Result<Venue>
    suspend fun getVenuesByTextQuery(query: String): Result<List<Venue>>
}