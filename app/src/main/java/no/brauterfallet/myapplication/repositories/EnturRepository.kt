package no.brauterfallet.myapplication.repositories

import no.brauterfallet.myapplication.dto.DepartureDTO
import no.brauterfallet.myapplication.dto.GeocoderFeature
import no.brauterfallet.myapplication.dto.GeocoderVenue
import no.brauterfallet.myapplication.models.VenueWithDepartures

interface EnturRepository {
    suspend fun getClosestVenue(latitude: Double, longitude: Double): Result<GeocoderVenue>
    suspend fun getVenuesByTextQuery(query: String): Result<List<GeocoderVenue>>

    suspend fun getClosestFeature(latitude: Double, longitude: Double): Result<GeocoderFeature>
    suspend fun getFeaturesByTextQuery(query: String): Result<List<GeocoderFeature>>

    suspend fun getDeparturesFromVenue(venueId: String): Result<List<DepartureDTO>>
    suspend fun getVenueWithDeparturesFromGeocoderFeature(feature: GeocoderFeature): Result<VenueWithDepartures>
}