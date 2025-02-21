package no.brauterfallet.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class GeocoderResponse(
    val features: List<Feature>
)

@Serializable
data class Feature(
    val properties: Venue
)

@Serializable
data class Venue(
    val id: String,
    val name: String,
    val distance: Float,
)