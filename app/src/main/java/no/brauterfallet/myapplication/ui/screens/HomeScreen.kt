package no.brauterfallet.myapplication.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import no.brauterfallet.myapplication.ui.components.LocationRequestCard
import no.brauterfallet.myapplication.ui.components.VenueCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel()) {
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (!locationPermissionState.status.isGranted) {
        Box(modifier = modifier.fillMaxSize()) {
            LocationRequestCard(
                modifier = Modifier.align(Alignment.Center),
                onClick = { locationPermissionState.launchPermissionRequest() })
        }
        return
    }

    val context = LocalContext.current
    LaunchedEffect(locationPermissionState) {
        viewModel.updateClosestVenue(context)
    }

    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val venue by viewModel.venue.collectAsStateWithLifecycle()
    val departures by viewModel.departures.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = homeScreenState == HomeScreenState.IS_LOADING,
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
                    VenueCard(venue, departures, modifier = Modifier.fillMaxSize())
                }

                HomeScreenState.IS_LOADING -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                HomeScreenState.FETCHING_LOCATION_FAILED -> {
                    Text(
                        text = "Noe gikk galt, pass på at posisjon er skrudd på og prøv igjen!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                HomeScreenState.FETCHING_DATA_FAILED -> {
                    Text(
                        text = "Noe gikk galt, pass på at du har nettverk og prøv igjen!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}