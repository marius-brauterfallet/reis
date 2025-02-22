package no.brauterfallet.myapplication.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeocoderResponse(
    val features: List<Feature>
)

@Serializable
data class Feature(
    val properties: Venue,
    val geometry: Geometry
)

@Serializable
data class Venue(
    val id: String,
    val name: String,
    val distance: Float? = null,
    val layer: String
)

@Serializable
data class Geometry(
    val coordinates: List<Float>
)