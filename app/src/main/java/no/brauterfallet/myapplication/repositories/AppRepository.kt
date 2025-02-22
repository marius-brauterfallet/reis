package no.brauterfallet.myapplication.repositories

import no.brauterfallet.myapplication.dto.Venue
import no.brauterfallet.myapplication.models.Departure

interface AppRepository {
    suspend fun getClosestVenue(latitude: Float, longitude: Float): Result<Venue>
    suspend fun getVenuesByTextQuery(query: String): Result<List<Venue>>
    suspend fun getDeparturesFromVenue(venueId: String): Result<List<Departure>>
}