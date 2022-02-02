package com.macrosystems.beerfinder.domain.repository

import com.macrosystems.beerfinder.data.model.BeerModel
import com.macrosystems.beerfinder.data.network.response.Result

interface SearchBeerRepository {
    suspend fun searchBeers(searchQuery: String): Result<List<BeerModel>>
}