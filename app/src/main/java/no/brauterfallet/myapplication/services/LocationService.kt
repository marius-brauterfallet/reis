package no.brauterfallet.myapplication.services

import android.content.Context
import android.location.Location

interface LocationService {
    suspend fun getLocation(context: Context): Location?
    fun getDistance(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Float
}