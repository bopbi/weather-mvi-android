package com.bobbyprabowo.weathernews.domain

import com.bobbyprabowo.weathernews.model.Weather
import io.reactivex.rxjava3.core.Single

interface LoadWeather {

    operator fun invoke(): Single<List<Weather>>
}
