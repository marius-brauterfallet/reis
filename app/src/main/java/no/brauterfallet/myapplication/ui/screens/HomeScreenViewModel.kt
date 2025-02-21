package no.brauterfallet.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.brauterfallet.myapplication.datasources.GeocoderDataSource
import no.brauterfallet.myapplication.models.Venue

class HomeScreenViewModel(
    private val geocoderDataSource: GeocoderDataSource
) : ViewModel() {
    private val _closestVenue = MutableStateFlow<Venue?>(null)
    val closestVenue = _closestVenue
        .onStart { updateClosestVenue() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateClosestVenue() {
        viewModelScope.launch {
            geocoderDataSource.getClosestVenue(59.927658f, 10.715266f)
                .onSuccess { _closestVenue.value = it }
        }
    }
}