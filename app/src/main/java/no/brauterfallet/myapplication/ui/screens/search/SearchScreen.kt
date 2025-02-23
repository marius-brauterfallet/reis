package no.brauterfallet.myapplication.ui.screens.search

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    val searchBarPadding: Dp by animateDpAsState(if (expanded) 0.dp else 16.dp)

    Column(
        modifier = modifier
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = { keyboardController?.hide() },
                    expanded = expanded,
                    onExpandedChange = viewModel::onExpandedChange,
                    placeholder = { Text("Søk etter stasjon") },
                )
            },
            expanded = expanded,
            onExpandedChange = viewModel::onExpandedChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(searchBarPadding)
        ) {
            LazyColumn {
                items(searchResultVenues) { venue ->
                    SearchResult(venue = venue, onClick = viewModel::onVenueClick)
                }
            }
        }

        if (selectedVenue != null) {
            HorizontalDivider()

            val scrollState = rememberScrollState()

            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = viewModel::onRefresh,
            ) {
                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    VenueCard(
                        selectedVenue,
                        departures,
                        modifier = Modifier
                            .padding(16.dp, 4.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}