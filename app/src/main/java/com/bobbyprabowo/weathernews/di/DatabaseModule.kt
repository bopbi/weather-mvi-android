package com.bobbyprabowo.weathernews.di

import android.content.Context
import com.bobbyprabowo.weathernews.Database
import com.bobbyprabowo.weathernews.WeatherQueries
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): Database {
        return Database(AndroidSqliteDriver(Database.Schema, appContext, "test.db"))
    }

    @Provides
    @Singleton
    fun provideWeatherQueries(database: Database): WeatherQueries {
        return database.weatherQueries
    }

}
