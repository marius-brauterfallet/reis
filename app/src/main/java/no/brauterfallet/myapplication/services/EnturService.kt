package no.brauterfallet.myapplication.services

import no.brauterfallet.myapplication.models.Venue
import no.brauterfallet.myapplication.models.VenueWithDepartures

interface EnturService {
    suspend fun getClosestVenueWithDepartures(latitude: Double, longitude: Double): Result<VenueWithDepartures>
    suspend fun getVenuesByQuery(query: String): Result<List<Venue>>
    suspend fun getVenueWithDepartures(venue: Venue): Result<VenueWithDepartures>
}