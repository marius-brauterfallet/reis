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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.brauterfallet.myapplication.models.Venue
import no.brauterfallet.myapplication.ui.theme.ReisTheme
import kotlin.math.roundToInt

@Composable
fun VenueCard(venue: Venue, modifier: Modifier = Modifier) {
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

            // TODO: Add lines here
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VenueCardPreview() {
    ReisTheme {
        VenueCard(Venue("", "Jernbanetorget", 1.1534f))
    }
}