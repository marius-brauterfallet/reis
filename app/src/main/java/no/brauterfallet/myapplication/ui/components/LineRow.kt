package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import no.brauterfallet.myapplication.models.Line
import no.brauterfallet.myapplication.models.TransportationMode
import no.brauterfallet.myapplication.ui.theme.ReisTheme

@Composable
fun LineRow(line: Line) {
    val currentDeparture = line.departures.first()
    val nextDepartures = line.departures.drop(1).take(3)
    val timeTextStyle = MaterialTheme.typography.labelMedium

    val expectedDepartureTruncated =
        currentDeparture.expectedDeparture.toLocalDateTime(TimeZone.currentSystemDefault())
            .let { LocalTime(it.hour, it.minute) }
    val aimedDepartureTruncated =
        currentDeparture.aimedDeparture.toLocalDateTime(TimeZone.currentSystemDefault())
            .let { LocalTime(it.hour, it.minute) }

    val departuresDiffer = expectedDepartureTruncated != aimedDepartureTruncated

    Card {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    LineNumberBox(line)
                    Text(
                        text = line.destination ?: "",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    if (departuresDiffer) {
                        Text(
                            text = aimedDepartureTruncated
                                .format(LocalTime.Format { hour(); char(':'); minute() }),
                            style = timeTextStyle.copy(textDecoration = TextDecoration.LineThrough),
                            maxLines = 1
                        )
                    }

                    Text(
                        text = expectedDepartureTruncated
                            .format(LocalTime.Format { hour(); char(':'); minute() }),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1
                    )
                }
            }

            if (nextDepartures.any()) {
                HorizontalDivider()

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Neste avganger:", style = timeTextStyle)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
                    ) {
                        nextDepartures.forEach { departure ->
                            val departureText = departure.expectedDeparture
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                .let { LocalTime(it.hour, it.minute) }
                                .format(LocalTime.Format { hour(); char(':'); minute() })

                            Text(text = departureText, style = timeTextStyle)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun LineNumberBox(line: Line) {
    val icon = when (line.transportationMode) {
        TransportationMode.AIR -> Icons.Default.AirplanemodeActive
        TransportationMode.BICYCLE -> Icons.Default.PedalBike
        TransportationMode.WATER -> Icons.Default.DirectionsBoat
        TransportationMode.RAIL -> Icons.Default.Train
        TransportationMode.METRO -> Icons.Default.Subway
        TransportationMode.TAXI -> Icons.Default.LocalTaxi
        TransportationMode.TRAM -> Icons.Default.Tram
        TransportationMode.COACH,
        TransportationMode.TROLLEYBUS,
        TransportationMode.BUS -> Icons.Default.DirectionsBus

        else -> Icons.Default.QuestionMark
    }

    val backgroundColor = line.color ?: Color.Gray
    val textColor = line.textColor ?: Color.Black

    Row(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .defaultMinSize(76.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(icon, contentDescription = null, tint = textColor)
        Text(line.lineNumber ?: "", color = textColor)
    }
}

@Composable
@Preview(showBackground = true)
fun LineRowPreview() {
    ReisTheme {
    }
}