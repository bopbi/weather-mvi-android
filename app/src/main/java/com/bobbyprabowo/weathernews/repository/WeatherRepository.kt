package com.bobbyprabowo.weathernews.repository

import com.bobbyprabowo.weathernews.model.Weather
import com.bobbyprabowo.weathernews.model.dto.WeatherRequest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by t-bobby.prabowo on 04,February,2022
 */
interface WeatherRepository {

    fun fetchOnlyWeather(weatherRequest: WeatherRequest) : Completable

    fun fetchWeather(weatherRequest: WeatherRequest) : Single<List<Weather>>

    fun getWeather() : Single<List<Weather>>
}
