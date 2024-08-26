package com.tapascodev.inspector

import com.tapascodev.inspector.auth.data.AuthRepositoryImpl
import com.tapascodev.inspector.auth.domain.AuthRepository
import com.tapascodev.inspector.lines.data.LineRepositoryImpl
import com.tapascodev.inspector.lines.domain.LineRepository
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.places.data.PlaceRepositoryImpl
import com.tapascodev.inspector.places.domain.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(firebaseClient: FirebaseClient): LineRepository {
        return LineRepositoryImpl(firebaseClient)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(firebaseClient: FirebaseClient): AuthRepository {
        return AuthRepositoryImpl(firebaseClient)
    }

    @Singleton
    @Provides
    fun providePlaceRepository(firebaseClient: FirebaseClient): PlaceRepository {
        return PlaceRepositoryImpl(firebaseClient)
    }

}