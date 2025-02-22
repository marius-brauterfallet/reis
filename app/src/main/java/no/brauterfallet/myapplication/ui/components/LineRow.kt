package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Subway
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.Tram
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.models.TransportationMode
import no.brauterfallet.myapplication.ui.theme.ReisTheme

@Composable
fun LineRow(departure: Departure) {
    Row(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LineNumberBox(departure)
            Text(departure.destination ?: "")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val expectedDepartureTruncated =
                departure.expectedDeparture.toLocalDateTime(TimeZone.currentSystemDefault())
                    .let { LocalTime(it.hour, it.minute) }
            val aimedDepartureTruncated =
                departure.aimedDeparture.toLocalDateTime(TimeZone.currentSystemDefault())
                    .let { LocalTime(it.hour, it.minute) }

            val departuresDiffer = expectedDepartureTruncated != aimedDepartureTruncated

            val timeTextStyle = MaterialTheme.typography.labelMedium

            Text(
                text = aimedDepartureTruncated.format(LocalTime.Format { hour(); char(':'); minute() }),
                style = if (departuresDiffer) timeTextStyle.copy(textDecoration = TextDecoration.LineThrough) else timeTextStyle,
            )

            if (departuresDiffer) {
                Text(
                    text = expectedDepartureTruncated.format(LocalTime.Format { hour(); char(':'); minute() }),
                    style = timeTextStyle
                )
            }
        }


    }
}

@Composable
fun LineNumberBox(departure: Departure) {
    val icon = when (departure.transportationMode) {
        TransportationMode.AIR -> Icons.Default.AirplanemodeActive
        TransportationMode.BICYCLE -> Icons.Default.PedalBike
        TransportationMode.BUS -> Icons.Default.DirectionsBus
        TransportationMode.WATER -> Icons.Default.DirectionsBoat
        TransportationMode.RAIL -> Icons.Default.Train
        TransportationMode.METRO -> Icons.Default.Subway
        TransportationMode.TAXI -> Icons.Default.LocalTaxi
        TransportationMode.TRAM -> Icons.Default.Tram
        TransportationMode.TROLLEYBUS -> Icons.Default.DirectionsBus
        else -> Icons.Default.QuestionMark
    }

    val backgroundColor = departure.color ?: Color.Gray
    val textColor = departure.textColor ?: Color.Black

    Row(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .defaultMinSize(76.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(icon, contentDescription = null, tint = textColor)
        Text(departure.lineNumber ?: "???", color = textColor)
    }
}

@Composable
@Preview(showBackground = true)
fun LineRowPreview() {
    ReisTheme {
        LineRow(
            Departure(
                lineNumber = "L14",
                transportationMode = TransportationMode.RAIL,
                destination = "Kongsvinger",
                expectedDeparture = Clock.System.now(),
                aimedDeparture = Clock.System.now(),
                actualDeparture = Clock.System.now(),
                color = Color("FFFF0000".toLong(16)),
                textColor = Color("FF000000".toLong(16)),
            )
        )
    }
}