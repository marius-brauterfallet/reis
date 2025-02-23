package no.brauterfallet.myapplication.mappers

import no.brauterfallet.myapplication.dto.DepartureDTO
import no.brauterfallet.myapplication.dto.GeocoderFeature
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.models.Line
import no.brauterfallet.myapplication.models.Venue
import no.brauterfallet.myapplication.models.VenueWithDepartures


object VenueWithDeparturesMapper {
    fun fromGeocoderFeatureAndDepartures(
        geocoderFeature: GeocoderFeature, departureDTOS: List<DepartureDTO>
    ): VenueWithDepartures {
        val lines = linesFromDepartures(departureDTOS)

        val geocoderVenue = geocoderFeature.properties
        val (longitude, latitude) = geocoderFeature.geometry.coordinates

        return VenueWithDepartures(
            id = geocoderVenue.id,
            name = geocoderVenue.name,
            label = geocoderVenue.label,
            latitude = latitude,
            longitude = longitude,
            lines = lines
        )
    }

    fun fromVenueAndDepartures(venue: Venue, departureDTOS: List<DepartureDTO>) = VenueWithDepartures(
        id = venue.id,
        name = venue.name,
        label = venue.label,
        latitude = venue.latitude,
        longitude = venue.longitude,
        distance = venue.distance,
        lines = linesFromDepartures(departureDTOS),
    )

    private fun linesFromDepartures(departureDTOS: List<DepartureDTO>): List<Line> {
        return departureDTOS.groupBy { departure -> departure.lineNumber to departure.destination }
            .map { (numberAndDestination, departures) ->
                val (lineNumber, destination) = numberAndDestination

                val lineDepartures = departures.map { departure ->
                    Departure(
                        expectedDeparture = departure.expectedDeparture,
                        aimedDeparture = departure.aimedDeparture,
                        actualDeparture = departure.actualDeparture
                    )
                }

                Line(
                    lineNumber = lineNumber,
                    transportationMode = departures.first().transportationMode,
                    destination = destination,
                    color = departures.first().color,
                    textColor = departures.first().textColor,
                    departures = lineDepartures,
                )
            }
    }
}