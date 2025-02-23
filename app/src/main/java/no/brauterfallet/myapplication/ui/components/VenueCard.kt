package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.brauterfallet.myapplication.models.VenueWithDepartures
import no.brauterfallet.myapplication.ui.theme.ReisTheme
import kotlin.math.roundToInt

@Composable
fun VenueCard(
    venue: VenueWithDepartures,
    modifier: Modifier = Modifier
) {
    val distanceString = venue.distance?.let { distance ->
        if (distance >= 1000) "(%.1f km)".format(distance / 1000f)
        else "(${(distance).roundToInt()} m)"
    } ?: ""

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            venue.lines.forEach { line -> LineRow(line) }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VenueCardPreview() {
    ReisTheme {
//        VenueCard()
    }
}