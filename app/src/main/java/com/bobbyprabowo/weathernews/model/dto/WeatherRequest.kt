package com.bobbyprabowo.weathernews.model.dto

/**
 * Created by t-bobby.prabowo on 04,February,2022
 */
data class WeatherRequest(
    val cityId: Int,
    val year: Int,
    val month: Int,
    val day: Int
)
