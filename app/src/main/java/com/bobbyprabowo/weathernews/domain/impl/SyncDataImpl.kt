package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.domain.SyncData
import com.bobbyprabowo.weathernews.model.dto.WeatherRequest
import com.bobbyprabowo.weathernews.repository.AnalyticsRepository
import com.bobbyprabowo.weathernews.repository.ProfileRepository
import com.bobbyprabowo.weathernews.repository.WeatherRepository
import io.reactivex.rxjava3.core.Completable

class SyncDataImpl(private val weatherRepository: WeatherRepository, private val profileRepository: ProfileRepository) : SyncData {
    override fun invoke(): Completable {

        val weatherRequest = WeatherRequest(
            cityId = 1047378,
            year = 2020,
            month = 11,
            day = 2
        )

        return Completable.merge(
            listOf(
                weatherRepository.fetchOnlyWeather(weatherRequest),
                profileRepository.fetchOnlyProfile()
            )
        )
    }
}
