package com.gc.weatherapp

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.compose.get
import org.koin.core.scope.get
import org.koin.dsl.module
import util.LocationProvider


//
// Created by Code For Android on 15/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//



val locationModule = module {
    single { LocationProvider(get()) }
}

