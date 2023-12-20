package ru.yotfr.sevenwindstestapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.yotfr.sevenwindstestapp.data.repository.AuthRepositoryImpl
import ru.yotfr.sevenwindstestapp.data.tokenstorage.TokenStorageImpl
import ru.yotfr.sevenwindstestapp.domain.repository.AuthRepository
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppBindsModule {

    @Singleton
    @Binds
    fun bindTokenStorage(
        tokenStorageImpl: TokenStorageImpl
    ): TokenStorage

    @Singleton
    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}