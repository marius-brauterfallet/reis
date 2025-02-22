package no.brauterfallet.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.brauterfallet.myapplication.dto.Venue
import no.brauterfallet.myapplication.models.Departure
import no.brauterfallet.myapplication.repositories.AppRepository

class HomeScreenViewModel(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _closestVenue = MutableStateFlow<Venue?>(null)
    val closestVenue = _closestVenue
        .onStart { updateClosestVenue() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _departures = MutableStateFlow<List<Departure>>(emptyList())
    val departures = _departures

    private val _isLoadingClosestVenue = MutableStateFlow(true)
    val isLoadingClosestVenue = _isLoadingClosestVenue.asStateFlow()

    fun updateClosestVenue() {
        viewModelScope.launch {
            _isLoadingClosestVenue.value = true
            val closestVenue = appRepository.getClosestVenue(59.927658f, 10.715266f).getOrElse {
                _isLoadingClosestVenue.value = false
                return@launch
            }

            _closestVenue.value = closestVenue

            val departures = appRepository.getDeparturesFromVenue(closestVenue.id).getOrElse {
                _isLoadingClosestVenue.value = false
                return@launch
            }

            _departures.value = departures
            _isLoadingClosestVenue.value = false
        }
    }
}