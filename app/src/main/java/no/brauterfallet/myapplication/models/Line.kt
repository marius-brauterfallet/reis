package no.brauterfallet.myapplication.models

import androidx.compose.ui.graphics.Color

data class Line(
    val lineNumber: String?,
    val transportationMode: TransportationMode?,
    val destination: String?,
    val color: Color?,
    val textColor: Color?,
    val departures: List<Departure>
)