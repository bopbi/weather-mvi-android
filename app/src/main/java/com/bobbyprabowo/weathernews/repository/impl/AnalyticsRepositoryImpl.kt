package com.bobbyprabowo.weathernews.repository.impl

import com.bobbyprabowo.weathernews.repository.AnalyticsRepository

class AnalyticsRepositoryImpl : AnalyticsRepository {
    override fun sendEventClicked() {
        println("event clicked")
    }

    override fun sendEventLastDataDisplayed() {
        println("event data displayed")
    }
}
