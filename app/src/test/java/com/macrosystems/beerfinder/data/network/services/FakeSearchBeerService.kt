package com.macrosystems.beerfinder.data.network.services

import com.macrosystems.beerfinder.data.model.BeerModel
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.domain.repository.SearchBeerRepository

class FakeSearchBeerService: SearchBeerRepository  {

    val beers = mutableListOf<BeerModel>()

    override suspend fun searchBeers(searchQuery: String): Result<List<BeerModel>> {
       return when {
           searchQuery.isEmpty() -> {
               Result.Success(mutableListOf())
           }
           else -> {
               Result.Success(beers)
           }

       }
    }
}