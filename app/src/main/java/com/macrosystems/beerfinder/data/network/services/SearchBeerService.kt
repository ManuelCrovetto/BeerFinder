package com.macrosystems.beerfinder.data.network.services

import com.macrosystems.beerfinder.data.model.BeerResponse
import com.macrosystems.beerfinder.data.network.punkapi.PunkAPI
import com.macrosystems.beerfinder.data.network.response.Result
import java.lang.Exception
import javax.inject.Inject


class SearchBeerService @Inject constructor(private val punkAPI: PunkAPI) {

    suspend fun searchBeers(searchQuery: String): Result<List<BeerResponse>>{
        return try {
            val response = punkAPI.searchBeers(searchQuery)
            if (response.isSuccessful){
                response.body()?.let { beersList ->
                    return@let Result.Success(beersList)
                } ?: run {
                    Result.Error(null)
                }
            } else {
                Result.Error(null)
            }
        } catch (e: Exception) {
            Result.Error(Exception("Network error"))
        }

    }
}