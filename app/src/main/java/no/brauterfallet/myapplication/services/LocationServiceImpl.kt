package no.brauterfallet.myapplication.services

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationServiceImpl : LocationService {
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(context: Context) =
        suspendCancellableCoroutine<Location?> { continuation ->
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val currentLocationRequest = CurrentLocationRequest.Builder()
                .setMaxUpdateAgeMillis(30_000)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .setDurationMillis(Long.MAX_VALUE)
                .build()

            fusedLocationClient.getCurrentLocation(currentLocationRequest, null)
                .addOnSuccessListener { location -> continuation.resume(location) }
                .addOnFailureListener { exception -> continuation.resumeWithException(exception) }

        }

    override fun getDistance(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Float {
        val results = FloatArray(1)

        Location.distanceBetween(
            latitude1,
            longitude1,
            latitude2,
            longitude2,
            results
        )

        return results.first()
    }
}