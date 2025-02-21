package no.brauterfallet.myapplication.datasources

import no.brauterfallet.myapplication.models.Venue

interface GeocoderDataSource {
    suspend fun getClosestVenue(latitude: Float, longitude: Float): Result<Venue>
}