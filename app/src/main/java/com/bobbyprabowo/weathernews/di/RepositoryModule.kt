package com.bobbyprabowo.weathernews.di

import com.bobbyprabowo.weathernews.WeatherQueries
import com.bobbyprabowo.weathernews.remote.WeatherService
import com.bobbyprabowo.weathernews.remote.impl.WeatherServiceImpl
import com.bobbyprabowo.weathernews.repository.WeatherRepository
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
}
