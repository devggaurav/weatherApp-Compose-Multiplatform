package util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class LocationProvider {


    actual suspend fun requestLocationPermission(): Boolean {
        logMessage("I am requested for permission")
        val locationManager = CLLocationManager()
        val status = CLLocationManager.authorizationStatus()
        return when (status) {
            kCLAuthorizationStatusNotDetermined -> {
                val deferred = suspendCancellableCoroutine<Boolean> { continuation ->
                    locationManager.requestWhenInUseAuthorization()
                    locationManager.delegate = object : NSObject(),
                        CLLocationManagerDelegateProtocol {
                        override fun locationManager(
                            manager: CLLocationManager,
                            didChangeAuthorizationStatus: CLAuthorizationStatus
                        ) {
                            if (didChangeAuthorizationStatus == kCLAuthorizationStatusAuthorizedWhenInUse) {
                                continuation.resume(true)
                            } else {
                                continuation.resume(false)
                            }
                            locationManager.delegate = null
                        }
                    }
                }
                deferred
            }

            kCLAuthorizationStatusAuthorizedWhenInUse -> true
            else -> false
        }
    }


    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getLastKnownLocation(): Location? = withContext(Dispatchers.Main) {
        if (!CLLocationManager.locationServicesEnabled()) {
            logMessage("Location services are disabled.")
            return@withContext null
        }
        logMessage("Location services are enabled.")

        val authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus != kCLAuthorizationStatusAuthorizedWhenInUse &&
            authorizationStatus != kCLAuthorizationStatusAuthorizedAlways) {
            logMessage("Location authorization status is not sufficient: $authorizationStatus")
            return@withContext null
        }
        logMessage("Location authorization status is sufficient.")

        val locationManager = CLLocationManager()
        val locationDeferred = CompletableDeferred<Location?>()

        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = didUpdateLocations.firstOrNull() as? CLLocation
                logMessage("Did update locations: $location")
                locationDeferred.complete(
                    location?.let {
                        Location(
                            it.coordinate.useContents { latitude },
                            it.coordinate.useContents { longitude }
                        )
                    }
                )
                manager.stopUpdatingLocation()
                manager.delegate = null // Clear the delegate to avoid retaining it
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                logMessage("Failed to get location: ${didFailWithError.localizedDescription}")
                locationDeferred.complete(null)
                manager.stopUpdatingLocation()
                manager.delegate = null // Clear the delegate to avoid retaining it
            }
        }

        locationManager.delegate = delegate

        logMessage("Requesting location updates.")
        locationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()

        locationDeferred.await()
    }


}