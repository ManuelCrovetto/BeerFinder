package com.macrosystems.beerfinder.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.macrosystems.beerfinder.core.providers.TestDispatchers
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.data.network.services.getOrAwaitValue
import com.macrosystems.beerfinder.domain.model.Beer
import com.macrosystems.beerfinder.domain.usecases.SearchBeersByName
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@OptIn(ExperimentalTime::class)
class BeerFinderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getBeersByName: SearchBeersByName

    private lateinit var getBeersViewModel: BeerFinderViewModel

    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testDispatchers = TestDispatchers()
        getBeersViewModel = BeerFinderViewModel(getBeersByName, testDispatchers)
        Dispatchers.setMain(testDispatchers.testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when searchBeers validates EMPTY string search query`() = runTest {
        coEvery { getBeersByName(EMPTY_STRING) } returns Result.Success(mockBeerList())
        getBeersViewModel.viewState.test(timeout = 3.seconds) {
            getBeersViewModel.searchBeers(EMPTY_STRING)
            awaitItem()
            val initialState = awaitItem()
            val finalState = awaitItem()
            assert(initialState.isLoading)
            assert(!finalState.isLoading && finalState.isEmptyList)
            cancelAndIgnoreRemainingEvents()
        }
        assertThat(getBeersViewModel.beerList.getOrAwaitValue()).isEmpty()

    }

    @Test
    fun `searchBeersByName returns a ResultError with null value`() = runTest {
        coEvery { getBeersByName(TEST_QUERY) } returns Result.Error(null)
        getBeersViewModel.viewState.test(timeout = 3.seconds) {
            getBeersViewModel.searchBeers(TEST_QUERY)
            awaitItem()
            val initialState = awaitItem()
            val finalState = awaitItem()
            assert(initialState.isLoading)
            assert(!finalState.isLoading && finalState.isEmptyList)
            cancelAndIgnoreRemainingEvents()
        }
        assertThat(getBeersViewModel.beerList.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun `searchBeersByName returns a ResultError with an exception and error message`() = runTest {
        coEvery { getBeersByName(TEST_QUERY) } returns Result.Error(Exception(ERROR_MESSAGE))
        getBeersViewModel.viewState.test(timeout = 3.seconds) {
            getBeersViewModel.searchBeers(TEST_QUERY)
            awaitItem()
            val initialState = awaitItem()
            val finalState = awaitItem()
            assert(initialState.isLoading)
            assert(!finalState.isLoading && finalState.error && finalState.isEmptyList)
            cancelAndConsumeRemainingEvents()
        }
        assertThat(getBeersViewModel.beerList.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun `searchBeersByName returns ResultSuccess with EMPTY LIST`() = runTest {
        coEvery { getBeersByName(TEST_QUERY) } returns Result.Success(emptyList())
        getBeersViewModel.viewState.test(timeout = 3.seconds) {
            getBeersViewModel.searchBeers(TEST_QUERY)
            awaitItem()
            val loadingState = awaitItem()
            val finalState = awaitItem()
            assert(loadingState.isLoading)
            assert(!finalState.isLoading && finalState.success && finalState.isEmptyList)
            cancelAndConsumeRemainingEvents()
        }
        assertThat(getBeersViewModel.beerList.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun `searchBeersByName returns ResultSuccess with NOT EMPTY list`() = runTest {
        coEvery { getBeersByName(TEST_QUERY) } returns Result.Success(mockBeerList())
        getBeersViewModel.viewState.test(timeout = 3.seconds) {
            getBeersViewModel.searchBeers(TEST_QUERY)
            awaitItem()
            val initialState = awaitItem()
            val finalState = awaitItem()
            assert(initialState.isLoading)
            assert(!finalState.isLoading && finalState.success)
            cancelAndIgnoreRemainingEvents()
        }
        val list = getBeersViewModel.beerList.getOrAwaitValue()
        assertThat(list).isNotEmpty()
        assertThat(list).hasSize(3)
        assertThat(list.first().name).isEqualTo("a")
    }

    private fun mockBeerList(): List<Beer> {
        val beerList = mutableListOf<Beer>()
        ('a'..'c').forEachIndexed { index, c ->
            beerList.add(
                Beer(
                    name = c.toString(),
                    image_url = "",
                    description = "",
                    alcoholByVolume = index.toString()
                )
            )
        }
        return beerList
    }

    companion object {
        const val EMPTY_STRING = ""
        const val TEST_QUERY = "cuzqueña"
        const val ERROR_MESSAGE = "network error"
    }
}