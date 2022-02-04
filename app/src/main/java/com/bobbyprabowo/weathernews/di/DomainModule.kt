package com.bobbyprabowo.weathernews.di

import com.bobbyprabowo.weathernews.domain.FetchWeather
import com.bobbyprabowo.weathernews.domain.LoadWeather
import com.bobbyprabowo.weathernews.domain.impl.FetchWeatherImpl
import com.bobbyprabowo.weathernews.domain.impl.LoadWeatherImpl
import com.bobbyprabowo.weathernews.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideFetchWeather(weatherRepository: WeatherRepository): FetchWeather {
        return FetchWeatherImpl(weatherRepository)
    }

    @Singleton
    @Provides
    fun provideLoadWeather(weatherRepository: WeatherRepository): LoadWeather {
        return LoadWeatherImpl(weatherRepository)
    }
}
