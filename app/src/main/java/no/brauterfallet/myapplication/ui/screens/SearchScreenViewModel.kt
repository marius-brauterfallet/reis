package no.brauterfallet.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.brauterfallet.myapplication.dto.Venue
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.repositories.AppRepository

class SearchScreenViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _selectedVenue = MutableStateFlow<Venue?>(null)
    val selectedVenue = _selectedVenue.asStateFlow()

    private val _departures = MutableStateFlow<List<Departure>>(emptyList())
    val departures = _departures.asStateFlow()

    private val _searchResultVenues = MutableStateFlow<List<Venue>>(emptyList())
    val searchResultVenues = _searchResultVenues.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded.asStateFlow()


    fun onExpandedChange(value: Boolean) {
        _expanded.value = value
    }

    fun onQueryChange(query: String) {
        _query.value = query

        getVenuesByTextQuery(query)
    }

    fun onVenueClick(venue: Venue) {
        _query.value = ""
        _expanded.value = false
        _selectedVenue.value = venue

        viewModelScope.launch {
            _departures.value =
                appRepository.getDeparturesFromVenue(venue.id).getOrDefault(emptyList())
        }
    }

    private var currentQueryJob: Job? = null
    private fun getVenuesByTextQuery(query: String) {
        currentQueryJob?.cancel()

        if (query.isEmpty()) {
            _searchResultVenues.value = emptyList()
            return
        }

        currentQueryJob = viewModelScope.launch {
            // Delaying for half a second, to avoid making necessary API calls
            delay(500)
            _searchResultVenues.value =
                appRepository.getVenuesByTextQuery(query).getOrDefault(emptyList())
        }
    }
}