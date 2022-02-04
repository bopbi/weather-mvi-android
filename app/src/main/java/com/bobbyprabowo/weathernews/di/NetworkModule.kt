package com.bobbyprabowo.weathernews.di

import com.bobbyprabowo.weathernews.remote.WeatherService
import com.bobbyprabowo.weathernews.remote.impl.WeatherServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }

        }
    }

    @Provides
    @Singleton
    fun provideWeatherService(httpClient: HttpClient): WeatherService {
        return WeatherServiceImpl(httpClient)
    }
}
