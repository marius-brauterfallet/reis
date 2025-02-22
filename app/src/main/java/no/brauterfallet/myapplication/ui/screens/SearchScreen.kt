package no.brauterfallet.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.brauterfallet.myapplication.ui.components.SearchResult
import no.brauterfallet.myapplication.ui.components.VenueCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = koinViewModel()
) {
    val searchResultVenues by viewModel.searchResultVenues.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val expanded by viewModel.expanded.collectAsStateWithLifecycle()
    val selectedVenue by viewModel.selectedVenue.collectAsStateWithLifecycle()
    val departures by viewModel.departures.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = {},
                    expanded = expanded,
                    onExpandedChange = viewModel::onExpandedChange,
                    placeholder = { Text("Søk etter stasjon") }
                )
            },
            expanded = expanded,
            onExpandedChange = viewModel::onExpandedChange,
            modifier = Modifier.fillMaxWidth()
        ) {
            searchResultVenues.forEach { venue ->
                SearchResult(venue = venue, onClick = viewModel::onVenueClick)
            }
        }

        if (selectedVenue != null) {
            VenueCard(selectedVenue, departures)
        }
    }
}