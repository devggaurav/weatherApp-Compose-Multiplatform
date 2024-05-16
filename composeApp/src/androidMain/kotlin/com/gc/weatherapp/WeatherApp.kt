package com.gc.weatherapp

import android.app.Application
import util.KoinInitializer


//
// Created by Code For Android on 15/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).initialize()
    }
}