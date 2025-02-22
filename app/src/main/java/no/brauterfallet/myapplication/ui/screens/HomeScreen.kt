package no.brauterfallet.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.brauterfallet.myapplication.ui.components.VenueCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel()) {
    val closestVenue by viewModel.closestVenue.collectAsStateWithLifecycle()
    val departures by viewModel.departures.collectAsStateWithLifecycle()
    val isLoadingClosestVenue by viewModel.isLoadingClosestVenue.collectAsStateWithLifecycle()

//    var searchBarText by rememberSaveable { mutableStateOf("") }
//    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }
    var isRefreshing by rememberSaveable { mutableStateOf(false) }

//    Column(
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = modifier.padding(8.dp)
//    ) {
//        VenueSearchBar(
//            searchBarText = searchBarText,
//            onQueryChange = {
//                searchBarText = it
//
//            },
//            expanded = searchBarExpanded,
//            onExpandedChange = {
//                searchBarExpanded = it
//                if (!it) searchBarText = ""
//            }
//        ) {
//            // TODO: Add search results here
//        }

        PullToRefreshBox(
            isRefreshing = isLoadingClosestVenue,
            onRefresh = {
                isRefreshing = isLoadingClosestVenue
                viewModel.updateClosestVenue()
            },
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                VenueCard(closestVenue, departures, isLoadingClosestVenue)
            }
        }
//    }
}