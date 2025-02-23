package no.brauterfallet.myapplication.models

import kotlinx.datetime.Instant

data class Departure(
    val expectedDeparture: Instant,
    val aimedDeparture: Instant,
    val actualDeparture: Instant?,
)