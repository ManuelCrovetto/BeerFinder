package com.macrosystems.beerfinder.data.network.respository

import com.macrosystems.beerfinder.data.model.BeerModel
import com.macrosystems.beerfinder.data.network.punkapi.PunkAPI
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.domain.repository.SearchBeerRepository
import java.lang.Exception
import javax.inject.Inject

class SearchBeerRepositoryImpl @Inject constructor(private val punkAPI: PunkAPI): SearchBeerRepository {

    override suspend fun searchBeers(searchQuery: String): Result<List<BeerModel>> {
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