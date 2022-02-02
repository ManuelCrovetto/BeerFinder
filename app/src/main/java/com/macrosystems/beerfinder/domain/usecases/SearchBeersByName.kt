package com.macrosystems.beerfinder.domain.usecases

import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.domain.model.Beer
import com.macrosystems.beerfinder.domain.model.toDomain
import com.macrosystems.beerfinder.domain.repository.SearchBeerRepository
import javax.inject.Inject

class SearchBeersByName @Inject constructor(private val searchBeersRepository: SearchBeerRepository) {

    suspend operator fun invoke(query: String): Result<List<Beer>> {
        return when (val result = searchBeersRepository.searchBeers(query)) {
            is Result.Error -> Result.Error(Exception(result.message))
            is Result.Success -> Result.Success(result.data.map { it.toDomain() })
        }
    }
}