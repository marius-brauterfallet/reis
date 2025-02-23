package no.brauterfallet.myapplication.services

import no.brauterfallet.myapplication.mappers.VenueWithDeparturesMapper
import no.brauterfallet.myapplication.models.Venue
import no.brauterfallet.myapplication.models.VenueWithDepartures
import no.brauterfallet.myapplication.repositories.EnturRepository

class EnturServiceImpl(
    private val enturRepository: EnturRepository
) : EnturService {
    override suspend fun getClosestVenueWithDepartures(
        latitude: Double,
        longitude: Double
    ): Result<VenueWithDepartures> {
        return runCatching {
            val feature = enturRepository.getClosestFeature(latitude, longitude).getOrThrow()
            val departures = enturRepository.getDeparturesFromVenue(feature.properties.id).getOrThrow()

            VenueWithDeparturesMapper.fromGeocoderFeatureAndDepartures(feature, departures)
        }
    }

    override suspend fun getVenuesByQuery(query: String): Result<List<Venue>> {
        return runCatching {
            val features = enturRepository.getFeaturesByTextQuery(query).getOrThrow()
            features.map {
                val venue = it.properties
                val (longitude, latitude) = it.geometry.coordinates

                Venue(
                    id = venue.id,
                    name = venue.name,
                    label = venue.label,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
    }

    override suspend fun getVenueWithDepartures(venue: Venue): Result<VenueWithDepartures> {
        return runCatching {
            val departures = enturRepository.getDeparturesFromVenue(venue.id).getOrThrow()
            VenueWithDeparturesMapper.fromVenueAndDepartures(venue, departures)
        }
    }
}