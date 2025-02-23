package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import no.brauterfallet.myapplication.models.Venue
import kotlin.math.roundToInt

@Composable
fun SearchResult(venue: Venue, onClick: (Venue) -> Unit, modifier: Modifier = Modifier) {
    val distanceString = venue.distance?.let { distance ->
        if (distance >= 1000) "%.1f km".format(distance / 1000f)
        else "${(distance).roundToInt()} m"
    }

    Card(
        onClick = { onClick(venue) },
        shape = RectangleShape,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 12.dp)
        ) {
            Text(text = venue.label)

            distanceString?.let { Text(text = it) }
        }
    }
}