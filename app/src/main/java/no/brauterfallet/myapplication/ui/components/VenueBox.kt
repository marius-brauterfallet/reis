package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.models.TransportationMode
import no.brauterfallet.myapplication.dto.Venue
import no.brauterfallet.myapplication.ui.theme.ReisTheme
import kotlin.math.roundToInt

@Composable
fun VenueCard(venue: Venue, departures: List<Departure>, modifier: Modifier = Modifier) {
    val distanceString =
        if (venue.distance > 1) "(%.1f km)".format(venue.distance)
        else "(${(venue.distance * 1000).roundToInt()} m)"

    Card(modifier = modifier.padding(4.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(venue.name, style = MaterialTheme.typography.titleMedium)
                Text(text = distanceString, style = MaterialTheme.typography.labelMedium)
            }

            HorizontalDivider()

            departures.forEach { departure -> LineRow(departure) }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VenueCardPreview() {
    ReisTheme {
        VenueCard(
            Venue("", "Jernbanetorget", 1.1534f), listOf(
                Departure(
                    lineNumber = "L14",
                    transportationMode = TransportationMode.RAIL,
                    destination = "Kongsvinger",
                    expectedDeparture = Clock.System.now(),
                    aimedDeparture = Clock.System.now(),
                    actualDeparture = Clock.System.now(),
                    color = Color("FFFF0000".toLong(16)),
                    textColor = Color("FF000000".toLong(16)),
                ),
                Departure(
                    lineNumber = "L14",
                    transportationMode = TransportationMode.RAIL,
                    destination = "Asker",
                    expectedDeparture = Clock.System.now(),
                    aimedDeparture = Clock.System.now(),
                    actualDeparture = Clock.System.now(),
                    color = Color("FFFF0000".toLong(16)),
                    textColor = Color("FF000000".toLong(16))
                )
            )
        )
    }
}