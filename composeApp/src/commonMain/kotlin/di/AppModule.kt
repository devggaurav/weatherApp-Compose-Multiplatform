package di

import com.russhwolf.settings.Settings
import data.remote.api.WeatherApiImpl
import domain.WeatherApiService
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.HomeScreenViewModel


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

val appModule = module {
    single { Settings() }
    single<WeatherApiService>{ WeatherApiImpl()}
    factory {
        HomeScreenViewModel(
            api = get()
        )
    }

}

fun initializeKoin() {
    startKoin {

        modules(appModule)
    }
}