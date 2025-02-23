package no.brauterfallet.myapplication.ui.screens.home

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import no.brauterfallet.myapplication.ui.components.LocationRequestCard
import no.brauterfallet.myapplication.ui.components.VenueBox
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel()) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    if (locationPermissionsState.permissions.none { it.status.isGranted }) {
        Box(modifier = modifier.fillMaxSize()) {
            LocationRequestCard(
                modifier = Modifier.align(Alignment.Center),
                onClick = { locationPermissionsState.launchMultiplePermissionRequest() })
        }
        return
    }

    val context = LocalContext.current
    LaunchedEffect(locationPermissionsState) {
        viewModel.updateClosestVenue(context, initialLoad = true)
    }

    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val venue by viewModel.venue.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = homeScreenState == HomeScreenState.IS_REFRESHING,
        onRefresh = { viewModel.onRefresh(context) },
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            when (homeScreenState) {
                HomeScreenState.OK -> {
                    if (venue != null) {
                        venue?.let {
                            VenueBox(
                                venue = it,
                                modifier = Modifier
                                    .padding(16.dp, 4.dp)
                                    .fillMaxSize()
                            )
                        }
                    } else {
                        Text(
                            text = "Noe gikk galt, pass på at du har nettverk og prøv igjen!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).padding(16.dp)
                        )
                    }
                }

                HomeScreenState.IS_LOADING,
                HomeScreenState.IS_REFRESHING -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                HomeScreenState.FETCHING_LOCATION_FAILED -> {
                    Text(
                        text = "Noe gikk galt, pass på at posisjon er skrudd på og prøv igjen!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }

                HomeScreenState.FETCHING_DATA_FAILED -> {
                    Text(
                        text = "Noe gikk galt, pass på at du har nettverk og prøv igjen!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
            }
        }
    }
}