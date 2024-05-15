package util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CompletableDeferred
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError

actual class LocationProvider {

    private val locationManager = CLLocationManager()
    private var lastLocationDeferred: CompletableDeferred<Location?>? = null
    actual suspend fun requestLocationPermission(): Boolean {
        locationManager.requestWhenInUseAuthorization()
        return true
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getLastKnownLocation(): Location? {
        lastLocationDeferred = CompletableDeferred()
        locationManager.delegate = object : CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                // Retrieve the most recent location from the list
                val location = didUpdateLocations.lastOrNull() as? CLLocation
                lastLocationDeferred?.complete(
                    if (location != null) Location(location.coordinate, location.coordinate.longitude)
                    else null
                )
                // Clear the delegate to avoid retaining it
                locationManager.delegate = null
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                lastLocationDeferred?.complete(null)
                locationManager.delegate = null
            }
        }
        locationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters
        locationManager.requestLocation()
        return lastLocationDeferred?.await()
    }

}