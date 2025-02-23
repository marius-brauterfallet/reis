package no.brauterfallet.myapplication.models

data class VenueWithDepartures(
    val id: String,
    val name: String,
    val label: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Float? = null,
    val lines: List<Line>
)