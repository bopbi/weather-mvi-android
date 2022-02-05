package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.domain.LogLastDataDisplayed
import com.bobbyprabowo.weathernews.repository.AnalyticsRepository

class LogLastDataDisplayedImpl(private val analyticsRepository: AnalyticsRepository) : LogLastDataDisplayed {
    override fun invoke() {
        return analyticsRepository.sendEventLastDataDisplayed()
    }
}
