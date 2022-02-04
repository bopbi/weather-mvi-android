package com.bobbyprabowo.weathernews.repository.impl

import com.bobbyprabowo.weathernews.WeatherQueries
import com.bobbyprabowo.weathernews.model.Weather
import com.bobbyprabowo.weathernews.model.dto.WeatherRequest
import com.bobbyprabowo.weathernews.remote.WeatherService
import com.bobbyprabowo.weathernews.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherRepositoryImpl(private val weatherService: WeatherService, private val weatherQueries: WeatherQueries) : WeatherRepository {
    override fun fetchWeather(weatherRequest: WeatherRequest): Single<List<Weather>> {
        return weatherService.fetchWeathers(weatherRequest).doOnSuccess { weatherResponses ->
            weatherQueries.deleteAll()
            weatherResponses.forEach { weatherResponse ->
                weatherQueries.insert(weatherResponse.weatherStateName, weatherResponse.predictability.toLong())
            }
        }.ignoreElement().andThen(
            getWeather()
        )
    }

    override fun getWeather(): Single<List<Weather>> {
        return Single.defer {
            val result = weatherQueries.selectAll().executeAsList().map { weatherRow ->
                Weather(
                    weatherStateName = weatherRow.weather_state_name,
                    predictability = weatherRow.predictability.toInt()
                )
            }

            Single.just(result)
        }
    }
}
