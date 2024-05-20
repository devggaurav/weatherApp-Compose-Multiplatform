package util

import platform.CoreLocation.CLLocationManager
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject
import kotlin.coroutines.resume

//
// Created by Code For Android on 20/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class PermissionHandlerIos {

    suspend fun requestLocationPermission(): Boolean {
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

}