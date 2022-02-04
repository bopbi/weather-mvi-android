package com.bobbyprabowo.weathernews.remote

import com.bobbyprabowo.weathernews.model.dto.WeatherRequest
import com.bobbyprabowo.weathernews.model.dto.WeatherResponse
import io.reactivex.rxjava3.core.Single

interface WeatherService {

    fun fetchWeathers(weatherRequest: WeatherRequest): Single<List<WeatherResponse>>
}
