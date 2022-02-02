package com.macrosystems.beerfinder.di

import com.macrosystems.beerfinder.data.model.constants.Constants.BASE_URL
import com.macrosystems.beerfinder.data.network.punkapi.PunkAPI
import com.macrosystems.beerfinder.data.network.respository.SearchBeerRepositoryImpl
import com.macrosystems.beerfinder.domain.repository.SearchBeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRetrofitInstance(): PunkAPI{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
            .create(PunkAPI::class.java)
    }

    @Provides
    fun providesSearchBeersRepository(punkAPI: PunkAPI): SearchBeerRepository {
        return SearchBeerRepositoryImpl(punkAPI)
    }
}