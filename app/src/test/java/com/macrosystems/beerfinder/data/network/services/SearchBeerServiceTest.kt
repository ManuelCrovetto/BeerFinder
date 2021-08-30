package com.macrosystems.beerfinder.data.network.services

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.macrosystems.beerfinder.data.model.BeerResponse
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.domain.SearchBeersByName
import com.macrosystems.beerfinder.ui.viewmodel.BeerFinderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class SearchBeerServiceTest {

    @Mock
    private lateinit var context: Application

    private lateinit var beersViewModel: BeerFinderViewModel

    @Mock
    private lateinit var searchBeersByName: SearchBeersByName


    private lateinit var beerList: List<BeerResponse>

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        BDDMockito.`when`(context.applicationContext).thenReturn(context)

        Dispatchers.setMain(testDispatcher)

        mockData()

        beersViewModel = BeerFinderViewModel(searchBeersByName)

    }
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `retrieving beers with viewModel returns empty list`() {
        runBlockingTest {
            `when`(searchBeersByName.invoke("")).thenReturn(Result.Success(emptyList()))

            beersViewModel.searchBeers("")

        }
        assertTrue(beersViewModel.beerList.getOrAwaitValue().isEmpty())
    }

    @Test
    fun `retrieving beers with viewModel returns valid list`() {
        runBlockingTest {
            `when`(searchBeersByName.invoke("test")).thenReturn(Result.Success(beerList))

            beersViewModel.searchBeers("test")

        }
        assertEquals(3, beersViewModel.beerList.getOrAwaitValue().size)
    }


    private fun mockData(){
        beerList = listOf(
            BeerResponse(
                name = "Estrella Galicia",
                image_url = "",
                description = "Yummy spanish beer!",
                alcoholByVolume = "4.5"
            ),
            BeerResponse(
                name = "Heiniken",
                image_url = "",
                description = "World famous beer, not bad.",
                alcoholByVolume = "4.9"
            ),
            BeerResponse(
                name = "Cuzqueña",
                image_url = "",
                description = "Peruvian beer made with MachuPichu water",
                alcoholByVolume = "4.7"
            )
        )
    }
}