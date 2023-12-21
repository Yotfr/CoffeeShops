package ru.yotfr.sevenwindstestapp

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("2545e986-c9c4-4917-80d9-2952e9b96996")
    }
}