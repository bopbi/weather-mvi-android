package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.domain.LoadWeather
import com.bobbyprabowo.weathernews.model.Weather
import io.reactivex.rxjava3.core.Single

class LoadWeatherImpl : LoadWeather {
    override fun invoke(): Single<List<Weather>> {
        return Single.just(emptyList())
    }
}
