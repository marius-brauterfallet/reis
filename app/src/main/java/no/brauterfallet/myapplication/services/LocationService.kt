package no.brauterfallet.myapplication.services

import android.content.Context
import android.location.Location

interface LocationService {
    suspend fun getLocation(context: Context): Location?
}