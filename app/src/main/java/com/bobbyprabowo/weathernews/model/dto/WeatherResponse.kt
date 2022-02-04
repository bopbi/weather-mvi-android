package com.bobbyprabowo.weathernews.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("weather_state_name")
    val weatherStateName: String,

    @SerialName("predictability")
    val predictability: Int
) {
}
