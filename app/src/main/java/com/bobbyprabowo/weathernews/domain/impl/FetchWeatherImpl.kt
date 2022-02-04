package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.domain.FetchWeather
import com.bobbyprabowo.weathernews.model.Weather
import com.bobbyprabowo.weathernews.model.dto.WeatherRequest
import com.bobbyprabowo.weathernews.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class FetchWeatherImpl(private val weatherRepository: WeatherRepository) : FetchWeather {
    override fun invoke(): Single<List<Weather>> {
        val weatherRequest = WeatherRequest(
            cityId = 1047378,
            year = 2020,
            month = 11,
            day = 2
        )
        return weatherRepository.fetchWeather(weatherRequest)
    }
}
