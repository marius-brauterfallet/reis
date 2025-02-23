package no.brauterfallet.myapplication.ui.screens.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.brauterfallet.myapplication.models.Venue
import no.brauterfallet.myapplication.models.VenueWithDepartures
import no.brauterfallet.myapplication.services.EnturService
import no.brauterfallet.myapplication.services.LocationService

class SearchScreenViewModel(
    private val enturService: EnturService,
    private val locationService: LocationService
) : ViewModel() {
    private val _selectedVenue = MutableStateFlow<Venue?>(null)
    val selectedVenue = _selectedVenue.asStateFlow()

    private val _venueWithDepartures = MutableStateFlow<VenueWithDepartures?>(null)
    val venueWithDepartures = _venueWithDepartures.asStateFlow()

    private val _searchResultVenues = MutableStateFlow<List<Venue>>(emptyList())
    val searchResultVenues = _searchResultVenues.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded.asStateFlow()

    private val _screenState = MutableStateFlow(SearchScreenState.OK)
    val screenState = _screenState.asStateFlow()

    fun onExpandedChange(value: Boolean) {
        _expanded.value = value
    }

    fun onQueryChange(query: String, context: Context) {
        _query.value = query

        getVenuesByTextQuery(query, context)
    }

    fun onVenueClick(venue: Venue) {
        _query.value = ""
        _searchResultVenues.value = emptyList()
        _expanded.value = false
        _selectedVenue.value = venue
        _screenState.value = SearchScreenState.IS_LOADING_DEPARTURES

        loadVenueWithDepartures()
    }

    fun onRefresh() {
        _screenState.value = SearchScreenState.IS_REFRESHING
        loadVenueWithDepartures()
    }

    private fun loadVenueWithDepartures() {
        val venue = _selectedVenue.value ?: return

        viewModelScope.launch {
            runCatching {
                _venueWithDepartures.value = enturService.getVenueWithDepartures(venue).getOrThrow()
                _screenState.value = SearchScreenState.OK
            }
        }
    }

    private var currentQueryJob: Job? = null

    private fun getVenuesByTextQuery(query: String, context: Context) {
        currentQueryJob?.cancel()

        if (query.isEmpty()) {
            _searchResultVenues.value = emptyList()
            return
        }

        currentQueryJob = viewModelScope.launch {
            // Delaying for half a second, to avoid making necessary API calls
            delay(500)
            val deferredLocation = async { locationService.getLocation(context) }
            val deferredSearchResults = async {
                enturService.getVenuesByQuery(query).getOrDefault(emptyList())
            }

            val location = deferredLocation.await()
            val searchResults = deferredSearchResults.await()

            if (location == null) {
                _searchResultVenues.value = searchResults
                return@launch
            }

            _searchResultVenues.value = searchResults.map { venue ->
                val distance = locationService.getDistance(
                    location.latitude,
                    location.longitude,
                    venue.latitude,
                    venue.longitude
                )
                venue.copy(distance = distance)
            }
        }
    }
}