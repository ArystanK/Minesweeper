package kz.arctan.minesweaper.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.arctan.minesweaper.auth.data.AuthRepositoryFirebase
import kz.arctan.minesweaper.auth.domain.AuthRepository
import kz.arctan.minesweaper.auth.domain.usecases.LoginUseCase
import kz.arctan.minesweaper.auth.domain.usecases.RegisterUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryFirebase()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegistrationUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }
}