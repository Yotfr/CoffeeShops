package ru.yotfr.sevenwindstestapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yotfr.sevenwindstestapp.data.locationprovider.LocationProviderImpl
import ru.yotfr.sevenwindstestapp.domain.locationprovider.LocationProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationProvider(
        locationClient: FusedLocationProviderClient,
        @ApplicationContext context: Context
    ): LocationProvider {
        return LocationProviderImpl(
            locationClient, context
        )
    }
}