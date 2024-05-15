package util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class LocationProvider(
    private val context: Context,
    private val locationManager: FusedLocationProviderClient
) {
    actual suspend fun requestLocationPermission(): Boolean {

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        return !(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled)

    }

    @SuppressLint("MissingPermission")
    actual suspend fun getLastKnownLocation(): Location? {
        return suspendCancellableCoroutine { cont ->
            locationManager.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(
                            Location(
                                latitude = result.latitude,
                                longitude = result.longitude
                            )
                        )
                    } else {
                        cont.resume(null)
                    }

                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(
                        Location(
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    )
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }


            }

        }
    }

}