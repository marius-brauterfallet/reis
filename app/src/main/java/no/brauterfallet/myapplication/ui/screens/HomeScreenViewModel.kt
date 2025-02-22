package no.brauterfallet.myapplication.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.brauterfallet.myapplication.dto.Venue
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.repositories.AppRepository
import no.brauterfallet.myapplication.services.LocationService

class HomeScreenViewModel(
    private val appRepository: AppRepository,
    private val locationService: LocationService
) : ViewModel() {
    private val _venue = MutableStateFlow<Venue?>(null)
    val venue = _venue.asStateFlow()

    private val _departures = MutableStateFlow<List<Departure>>(emptyList())
    val departures = _departures.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState.OK)
    val homeScreenState = _homeScreenState.asStateFlow()

    fun onRefresh(context: Context) {
        updateClosestVenue(context)
    }

    fun updateClosestVenue(context: Context) {
        _homeScreenState.value = HomeScreenState.IS_LOADING

        viewModelScope.launch {
            val location = locationService.getLocation(context)

            if (location == null) {
                _homeScreenState.value = HomeScreenState.FETCHING_LOCATION_FAILED
                return@launch
            }

            val closestVenue = appRepository.getClosestVenue(
                location.latitude.toFloat(),
                location.longitude.toFloat()
            ).getOrElse {
                _homeScreenState.value = HomeScreenState.FETCHING_DATA_FAILED
                return@launch
            }

            _venue.value = closestVenue

            val departures = appRepository.getDeparturesFromVenue(closestVenue.id).getOrElse {
                _homeScreenState.value = HomeScreenState.FETCHING_DATA_FAILED
                return@launch
            }

            _departures.value = departures
            _homeScreenState.value = HomeScreenState.OK
        }
    }
}