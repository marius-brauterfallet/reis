package no.brauterfallet.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.brauterfallet.myapplication.ui.components.VenueCard
import no.brauterfallet.myapplication.ui.components.VenueSearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel()) {
    val closestVenue by viewModel.closestVenue.collectAsStateWithLifecycle()
    val departures by viewModel.departures.collectAsStateWithLifecycle()
    val isLoadingClosestVenue by viewModel.isLoadingClosestVenue.collectAsStateWithLifecycle()

    var searchBarText by rememberSaveable { mutableStateOf("") }
    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        VenueSearchBar(
            searchBarText = searchBarText,
            onQueryChange = { searchBarText = it.also { println(it) } },
            expanded = searchBarExpanded,
            onExpandedChange = { searchBarExpanded = it }
        ) {
            // TODO: Add search results here
        }

        HorizontalDivider()

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            VenueCard(closestVenue, departures, isLoadingClosestVenue)
        }
    }
}