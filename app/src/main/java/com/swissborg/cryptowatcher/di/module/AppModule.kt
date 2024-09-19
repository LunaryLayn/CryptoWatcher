package com.swissborg.cryptowatcher.di.module

import android.content.Context
import com.swissborg.data.api.BitfinexApiService
import com.swissborg.data.constants.AppConstants
import com.swissborg.data.repository.BitfinexRepositoryImpl
import com.swissborg.data.repository.NetworkRepositoryImpl
import com.swissborg.domain.repository.BitfinexRepository
import com.swissborg.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideBitfinexApiService(retrofit: Retrofit): BitfinexApiService {
        return retrofit.create(BitfinexApiService::class.java)
    }

    @Provides
    fun provideNetworkRepository(
        @ApplicationContext context: Context
    ): NetworkRepository {
        return NetworkRepositoryImpl(context)
    }

    @Provides
    fun provideBitfinexRepository(apiService: BitfinexApiService): BitfinexRepository {
        return BitfinexRepositoryImpl(apiService)
    }


}