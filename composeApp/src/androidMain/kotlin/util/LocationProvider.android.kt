package util

actual class LocationProvider {
    actual suspend fun requestLocationPermission(): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun getLastKnownLocation(): Location? {
        TODO("Not yet implemented")
    }

}