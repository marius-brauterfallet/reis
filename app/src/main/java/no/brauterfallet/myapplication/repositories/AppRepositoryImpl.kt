package no.brauterfallet.myapplication.repositories

import androidx.compose.ui.graphics.Color
import no.brauterfallet.myapplication.datasources.GeocoderDataSource
import no.brauterfallet.myapplication.datasources.JourneyPlannerDataSource
import no.brauterfallet.myapplication.dto.Venue
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.models.TransportationMode

class AppRepositoryImpl(
    private val geoCoderDataSource: GeocoderDataSource,
    private val journeyPlannerDataSource: JourneyPlannerDataSource
) : AppRepository {
    override suspend fun getClosestVenue(latitude: Float, longitude: Float): Result<Venue> {
        return geoCoderDataSource.getClosestVenue(latitude, longitude)
    }

    override suspend fun getDeparturesFromVenue(venueId: String): Result<List<Departure>> {
        val departuresQuery = journeyPlannerDataSource.getDeparturesFromVenue(venueId).getOrElse {
            return Result.failure(it)
        }

        val stopPlace = departuresQuery.stopPlace ?: return Result.failure(NoSuchStopPlaceFound())

        val departures = stopPlace.estimatedCalls.map { estimatedCall ->
            val transportationMode = runCatching {
                TransportationMode.valueOf(
                    value = estimatedCall.serviceJourney.journeyPattern?.line?.transportMode?.rawValue?.uppercase()
                        ?: ""
                )
            }.getOrNull()

            val color =
                estimatedCall.serviceJourney.journeyPattern?.line?.presentation?.colour?.let { colorString ->
                    println(colorString)
                    Color("FF$colorString".toLong(16))
                }

            val textColor =
                estimatedCall.serviceJourney.journeyPattern?.line?.presentation?.textColour?.let { colorString ->
                    println(colorString)
                    Color("FF$colorString".toLong(16))
                }

            Departure(
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

        return Result.success(departures)
    }
}

class NoSuchStopPlaceFound : Throwable()