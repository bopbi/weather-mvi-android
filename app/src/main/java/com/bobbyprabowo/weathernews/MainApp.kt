package com.bobbyprabowo.weathernews

import android.app.Application
import com.bobbyprabowo.weathernews.domain.SyncData
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApp : Application() {

    @Inject
    lateinit var syncData: SyncData

    override fun onCreate() {
        super.onCreate()

        syncData()
    }
}
