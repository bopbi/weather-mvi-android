package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.domain.LoadWeather
import com.bobbyprabowo.weathernews.model.Weather
import com.bobbyprabowo.weathernews.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class LoadWeatherImpl(private val weatherRepository: WeatherRepository) : LoadWeather {
    override fun invoke(): Single<List<Weather>> {
        return weatherRepository.getWeather()
    }
}
