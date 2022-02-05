package com.bobbyprabowo.weathernews.di

import com.bobbyprabowo.weathernews.WeatherQueries
import com.bobbyprabowo.weathernews.model.Profile
import com.bobbyprabowo.weathernews.remote.WeatherService
import com.bobbyprabowo.weathernews.remote.impl.WeatherServiceImpl
import com.bobbyprabowo.weathernews.repository.AnalyticsRepository
import com.bobbyprabowo.weathernews.repository.ProfileRepository
import com.bobbyprabowo.weathernews.repository.WeatherRepository
import com.bobbyprabowo.weathernews.repository.impl.AnalyticsRepositoryImpl
import com.bobbyprabowo.weathernews.repository.impl.ProfileRepositoryImpl
import com.bobbyprabowo.weathernews.repository.impl.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherService: WeatherService, weatherQueries: WeatherQueries): WeatherRepository {
        return WeatherRepositoryImpl(weatherService, weatherQueries)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository {
        return ProfileRepositoryImpl(Profile(id = "fake", name = "fake"))
    }

    @Provides
    @Singleton
    fun provideAnalyticsRepository(): AnalyticsRepository {
        return AnalyticsRepositoryImpl()
    }
}
