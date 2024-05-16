package util

import org.koin.dsl.module


//
// Created by Code For Android on 16/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

val locationModule = module {
    single { LocationProvider() }
}