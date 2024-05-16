package util

import di.appModule
import org.koin.core.context.startKoin


//
// Created by Code For Android on 15/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

actual class KoinInitializer {

    actual fun initialize() {

        startKoin {
            modules(appModule, locationModule)

        }
    }

}