package com.macrosystems.beerfinder.domain

import com.google.common.truth.Truth.assertThat
import com.macrosystems.beerfinder.data.model.BeerModel
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.data.network.services.FakeSearchBeerService
import com.macrosystems.beerfinder.domain.model.Beer
import com.macrosystems.beerfinder.domain.usecases.SearchBeersByName
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchBeersByNameTest {
    private lateinit var searchBeersByName: SearchBeersByName
    private lateinit var fakeSearchBeerService: FakeSearchBeerService

    @Before
    fun setUp() {
        fakeSearchBeerService = FakeSearchBeerService()
        searchBeersByName = SearchBeersByName(fakeSearchBeerService)

        val beersInserted = mutableListOf<BeerModel>()
        ('a'..'z').forEachIndexed { index, c ->
            beersInserted.add(
                BeerModel(
                    name = c.toString(),
                    image_url = c.toString(),
                    description = c.toString(),
                    alcoholByVolume = index.toString()
                )
            )
        }

        runBlocking {
            beersInserted.forEach { fakeSearchBeerService.beers.add(it) }
        }
    }

    @Test
    fun `if search query is empty returns an empty list`() = runBlocking {
        val beers = when (val result = searchBeersByName("")) {
            is Result.Error -> throw Exception("")
            is Result.Success -> result.data
        }
        assertThat(beers.size).isEqualTo(0)
    }

    @Test
    fun `search query is not empty returns a list not empty`() = runBlocking {
        val beerList = when (val result = searchBeersByName("cuzqueña")) {
            is Result.Error -> 0
            is Result.Success -> result.data.size
        }

        assertThat(beerList).isGreaterThan(0)
    }

    @Test
    fun `use case maps correctly BeerModel to domain object beer`() = runBlocking {
        val beerList = when (val result = searchBeersByName("cuzqueña")) {
            is Result.Error -> throw Exception("")
            is Result.Success -> result.data
        }

        beerList.forEach {
            assertThat(it).isInstanceOf(Beer::class.java)
        }
    }
}