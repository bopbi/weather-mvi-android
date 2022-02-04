package com.bobbyprabowo.weathernews.remote.impl

import com.bobbyprabowo.weathernews.model.dto.WeatherRequest
import com.bobbyprabowo.weathernews.model.dto.WeatherResponse
import com.bobbyprabowo.weathernews.remote.WeatherService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.rx3.rxSingle

class WeatherServiceImpl(private val httpClient: HttpClient) : WeatherService {
    override fun fetchWeathers(weatherRequest: WeatherRequest): Single<List<WeatherResponse>> {
        return rxSingle {
            val url = "https://www.metaweather.com/api/location/${weatherRequest.cityId}/${weatherRequest.year}/${weatherRequest.month}/${weatherRequest.day}"
            val weatherResponses: Array<WeatherResponse> = httpClient.get(url)
            weatherResponses.toList()
        }
    }
}
