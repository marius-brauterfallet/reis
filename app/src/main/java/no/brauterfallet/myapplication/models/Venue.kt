package no.brauterfallet.myapplication.models

data class Venue(
    val id: String,
    val name: String,
    val label: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Float? = null
)