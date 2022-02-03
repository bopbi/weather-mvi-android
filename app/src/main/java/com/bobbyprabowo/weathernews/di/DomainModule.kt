package com.bobbyprabowo.weathernews.di

import com.bobbyprabowo.weathernews.domain.FetchWeather
import com.bobbyprabowo.weathernews.domain.LoadWeather
import com.bobbyprabowo.weathernews.domain.impl.FetchWeatherImpl
import com.bobbyprabowo.weathernews.domain.impl.LoadWeatherImpl
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
    fun provideFetchWeather(): FetchWeather {
        return FetchWeatherImpl()
    }

    @Singleton
    @Provides
    fun provideLoadWeather(): LoadWeather {
        return LoadWeatherImpl()
    }
}
