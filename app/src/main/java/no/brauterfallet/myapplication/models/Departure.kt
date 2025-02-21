package no.brauterfallet.myapplication.models

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Instant

data class Departure(
    val lineNumber: String?,
    val transportationMode: TransportationMode?,
    val destination: String?,
    val expectedDeparture: Instant,
    val aimedDeparture: Instant,
    val actualDeparture: Instant?,
    val color: Color?,
    val textColor: Color?,
)
