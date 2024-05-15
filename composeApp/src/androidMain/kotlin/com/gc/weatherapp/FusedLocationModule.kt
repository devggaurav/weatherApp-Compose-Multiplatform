package com.gc.weatherapp

import com.google.android.gms.location.LocationServices
import org.koin.dsl.module


//
// Created by Code For Android on 15/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

val fusedLocationModule = module {

    single {
        LocationServices.getFusedLocationProviderClient(get())
    }

}