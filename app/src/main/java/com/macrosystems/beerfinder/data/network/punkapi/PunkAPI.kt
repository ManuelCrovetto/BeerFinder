package com.macrosystems.beerfinder.data.network.punkapi

import com.macrosystems.beerfinder.data.model.BeerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkAPI {

    @GET("beers?")
    suspend fun searchBeers(
        @Query("beer_name") searchQuery: String
    ): Response<List<BeerResponse>>
}