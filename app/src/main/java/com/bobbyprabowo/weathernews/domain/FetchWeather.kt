package com.bobbyprabowo.weathernews.domain

import com.bobbyprabowo.weathernews.model.Weather
import com.bobbyprabowo.weathernews.model.dto.WeatherResponse
import io.reactivex.rxjava3.core.Single

interface FetchWeather {

    operator fun invoke() : Single<List<Weather>>
}
