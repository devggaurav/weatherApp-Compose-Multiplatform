package util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CompletableDeferred
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError
import platform.darwin.NSObject

actual class LocationProvider {

    private val locationManager = CLLocationManager()
    private var lastLocationDeferred: CompletableDeferred<Location?>? = null
    actual suspend fun requestLocationPermission(): Boolean {
        locationManager.requestWhenInUseAuthorization()
        return true
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getLastKnownLocation(): Location? {
        if (!CLLocationManager.locationServicesEnabled()) {
            return null
        }

        // Check authorization status
        val authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus != kCLAuthorizationStatusAuthorizedWhenInUse &&
            authorizationStatus != kCLAuthorizationStatusAuthorizedAlways
        ) {
            return null
        }

        // Set up delegate
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {

            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = didUpdateLocations.firstOrNull()
                lastLocationDeferred?.complete(
                    location?.let {
                        it as CLLocation
                        // Convert CLLocation to Location
                        it.coordinate.useContents {
                            Location(latitude, longitude)
                        }

                    }
                )
                // Clear the delegate to avoid retaining it
                locationManager.delegate = null
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                lastLocationDeferred?.complete(null)
                // Clear the delegate to avoid retaining it
                locationManager.delegate = null
            }
        }
        locationManager.delegate = delegate

        // Request location updates
        locationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters
        locationManager.requestLocation()

        // Wait for the result
        return lastLocationDeferred?.await()
    }


}