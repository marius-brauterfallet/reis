package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import no.brauterfallet.myapplication.dto.Venue

@Composable
fun SearchResult(venue: Venue, onClick: (Venue) -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = { onClick(venue) }, modifier = modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 12.dp)) {
            Text(text = venue.label)
        }
    }
}