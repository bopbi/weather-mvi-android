package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.domain.LogClick
import com.bobbyprabowo.weathernews.repository.AnalyticsRepository

class LogClickImpl(private val analyticsRepository: AnalyticsRepository) : LogClick {
    override fun invoke() {
        analyticsRepository.sendEventClicked()
    }
}
