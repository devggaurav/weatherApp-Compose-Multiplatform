package data.remote.api

import domain.WeatherApiService
import domain.model.RequestState
import domain.model.WeatherRealm
import domain.weather.WeatherInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import mappers.toWeatherInfo


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class WeatherApiImpl : WeatherApiService {


    companion object {
        const val ENDPOINT =
            "https://api.open-meteo.com/v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl"

    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        install(DefaultRequest) {

        }

    }


    override suspend fun getWeatherData(lat: Double, lon: Double): RequestState<WeatherInfo> {
        return try {
            val response = httpClient.get(ENDPOINT) {
                url {
                    parameters.append("latitude", lat.toString())
                    parameters.append("longitude", lon.toString())
                }
            }
            if (response.status.value == 200) {
                println("Api response: ${response.body<String>()}")
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString<WeatherRealm>(response.body())
                println("Api response 2: ${apiResponse.weatherData.time}")

                RequestState.Success(data = apiResponse.toWeatherInfo())
            } else {
                RequestState.Error(message = "Http Error code: ${response.status}")
            }


        } catch (ex: Exception) {
            println("Api ERROR ${ex.message.toString()}")
            RequestState.Error(ex.message.toString())
        }

    }


}