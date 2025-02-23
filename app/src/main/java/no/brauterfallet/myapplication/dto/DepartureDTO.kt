package no.brauterfallet.myapplication.dto

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Instant
import no.brauterfallet.myapplication.models.TransportationMode

data class DepartureDTO(
    val lineNumber: String?,
    val transportationMode: TransportationMode?,
    val destination: String?,
    val expectedDeparture: Instant,
    val aimedDeparture: Instant,
    val actualDeparture: Instant?,
    val color: Color?,
    val textColor: Color?,
)
