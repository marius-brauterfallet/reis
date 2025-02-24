package no.brauterfallet.myapplication.repositories

import androidx.compose.ui.graphics.Color
import no.brauterfallet.myapplication.datasources.GeocoderDataSource
import no.brauterfallet.myapplication.datasources.JourneyPlannerDataSource
import no.brauterfallet.myapplication.dto.DepartureDTO
import no.brauterfallet.myapplication.dto.GeocoderFeature
import no.brauterfallet.myapplication.mappers.VenueWithDeparturesMapper
import no.brauterfallet.myapplication.models.TransportationMode
import no.brauterfallet.myapplication.models.VenueWithDepartures

class EnturRepositoryImpl(
    private val geoCoderDataSource: GeocoderDataSource,
    private val journeyPlannerDataSource: JourneyPlannerDataSource
) : EnturRepository {
    override suspend fun getClosestFeature(
        latitude: Double,
        longitude: Double
    ): Result<GeocoderFeature> {
        return geoCoderDataSource.getClosestFeature(latitude, longitude)
    }

    override suspend fun getFeaturesByTextQuery(query: String): Result<List<GeocoderFeature>> {
        return geoCoderDataSource.getFeaturesByTextQuery(query)
    }

    override suspend fun getDeparturesFromVenue(venueId: String): Result<List<DepartureDTO>> {
        val departuresQuery = journeyPlannerDataSource.getDeparturesFromVenue(venueId).getOrElse {
            return Result.failure(it)
        }

        val stopPlace = departuresQuery.stopPlace ?: return Result.failure(NoSuchStopPlaceFound())

        val departureDTOS = stopPlace.estimatedCalls.map { estimatedCall ->
            val transportationMode = runCatching {
                TransportationMode.valueOf(
                    value = estimatedCall.serviceJourney.journeyPattern?.line?.transportMode?.rawValue?.uppercase()
                        ?: ""
                )
            }.getOrNull()

            val color =
                estimatedCall.serviceJourney.journeyPattern?.line?.presentation?.colour?.let { colorString ->
                    Color("FF$colorString".toLong(16))
                }

            val textColor =
                estimatedCall.serviceJourney.journeyPattern?.line?.presentation?.textColour?.let { colorString ->
                    Color("FF$colorString".toLong(16))
                }

            DepartureDTO(
                lineNumber = estimatedCall.serviceJourney.journeyPattern?.line?.publicCode,
                transportationMode = transportationMode,
                destination = estimatedCall.destinationDisplay?.frontText,
                expectedDeparture = estimatedCall.expectedDepartureTime,
                aimedDeparture = estimatedCall.aimedDepartureTime,
                actualDeparture = estimatedCall.actualDepartureTime,
                color = color,
                textColor = textColor
            )
        }

        return Result.success(departureDTOS)
    }

    override suspend fun getVenueWithDeparturesFromGeocoderFeature(feature: GeocoderFeature): Result<VenueWithDepartures> {
        return runCatching {
            val geocoderVenue = feature.properties
            val departures = getDeparturesFromVenue(geocoderVenue.id).getOrThrow()

            VenueWithDeparturesMapper.fromGeocoderFeatureAndDepartures(feature, departures)
        }
    }
}

class NoSuchStopPlaceFound : Throwable()