package util


//
// Created by Code For Android on 14/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

expect  class LocationProvider {

    suspend fun requestLocationPermission(): Boolean
    suspend fun getLastKnownLocation(): Location?
}

data class Location(
    val latitude: Double,
    val longitude: Double
)