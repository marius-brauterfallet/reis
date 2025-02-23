package no.brauterfallet.myapplication.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeocoderResponse(
    val features: List<GeocoderFeature>
)

@Serializable
data class GeocoderFeature(
    val properties: GeocoderVenue,
    val geometry: GeocoderGeometry
)

@Serializable
data class GeocoderVenue(
    val id: String,
    val name: String,
    val distance: Float? = null,
    val layer: String,
    val label: String
)

@Serializable
data class GeocoderGeometry(
    val coordinates: List<Double>
)