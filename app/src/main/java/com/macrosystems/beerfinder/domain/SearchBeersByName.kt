package com.macrosystems.beerfinder.domain

import com.macrosystems.beerfinder.data.model.BeerResponse
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.data.network.services.SearchBeerService
import javax.inject.Inject

class SearchBeersByName @Inject constructor(private val searchBeerService: SearchBeerService) {

    suspend operator fun invoke(query: String): Result<List<BeerResponse>> = searchBeerService.searchBeers(query)
}