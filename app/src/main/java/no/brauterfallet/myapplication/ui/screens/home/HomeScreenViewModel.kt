package no.brauterfallet.myapplication.ui.screens.home

import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.brauterfallet.myapplication.models.VenueWithDepartures
import no.brauterfallet.myapplication.services.EnturService
import no.brauterfallet.myapplication.services.LocationService

class HomeScreenViewModel(
    private val enturService: EnturService,
    private val locationService: LocationService
) : ViewModel() {
    private val _venue = MutableStateFlow<VenueWithDepartures?>(null)
    val venue = _venue.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState.OK)
    val homeScreenState = _homeScreenState.asStateFlow()

    fun onRefresh(context: Context) {
        updateClosestVenue(context)
    }

    fun updateClosestVenue(context: Context, initialLoad: Boolean = false) {
        _homeScreenState.value =
            if (initialLoad) HomeScreenState.IS_LOADING else HomeScreenState.IS_REFRESHING

        viewModelScope.launch {
            val location = locationService.getLocation(context)

            if (location == null) {
                _homeScreenState.value = HomeScreenState.FETCHING_LOCATION_FAILED
                return@launch
            }

            val venueWithDepartures = enturService.getClosestVenueWithDepartures(
                location.latitude,
                location.longitude
            ).getOrElse {
                _homeScreenState.value = HomeScreenState.FETCHING_DATA_FAILED
                return@launch
            }

            val results = FloatArray(1)
            Location.distanceBetween(
                location.latitude,
                location.longitude,
                venueWithDepartures.latitude,
                venueWithDepartures.longitude,
                results
            )

            println("First coordinates: ${location.latitude}, ${location.longitude}")
            println("Venue coordinates: ${venueWithDepartures.latitude}, ${venueWithDepartures.longitude}")

            val distance = results.first()
            println("Distance: $distance m")

            _venue.value = venueWithDepartures.copy(distance = results.first())
            _homeScreenState.value = HomeScreenState.OK
        }
    }
}