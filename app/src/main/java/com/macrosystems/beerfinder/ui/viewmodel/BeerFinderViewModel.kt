package com.macrosystems.beerfinder.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macrosystems.beerfinder.data.model.BeerResponse
import com.macrosystems.beerfinder.data.model.constants.Constants.SEARCH_DELAY_TIME
import com.macrosystems.beerfinder.data.network.response.Result
import com.macrosystems.beerfinder.domain.SearchBeersByName
import com.macrosystems.beerfinder.ui.view.viewstates.BeerFinderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerFinderViewModel @Inject constructor(private val searchBeersByName: SearchBeersByName): ViewModel() {

    private val _viewState = MutableStateFlow(BeerFinderViewState())
    val viewState: StateFlow<BeerFinderViewState>
        get() = _viewState

    private val _beerList = MutableLiveData<MutableList<BeerResponse>>()
    val beerList: LiveData<MutableList<BeerResponse>>
        get() = _beerList


    fun searchBeers(query: String){
        viewModelScope.launch(IO) {
            _viewState.value = BeerFinderViewState(isLoading = true)
            if (validateQuery(query)){
                delay(SEARCH_DELAY_TIME)

                when (val result = searchBeersByName(query.replaceSpacesWithUnderScores())){
                    is Result.Error -> {
                        result.message?.let {
                            _beerList.postValue(mutableListOf())
                            _viewState.value = BeerFinderViewState(isLoading = false, error = true, isEmptyList = true)
                        } ?: run {
                            _beerList.postValue(mutableListOf())
                            _viewState.value = BeerFinderViewState(isLoading = false, isEmptyList = true)
                        }

                    }

                    is Result.Success -> {
                        if (result.data.isEmpty()){
                            _beerList.postValue(result.data.toMutableList())
                            _viewState.value = BeerFinderViewState(isLoading = false, success = true, isEmptyList = true)
                        } else {
                            _beerList.postValue(result.data.toMutableList())
                            _viewState.value = BeerFinderViewState(isLoading = false, success = true)
                        }

                    }
                }

            } else {
                _viewState.value = BeerFinderViewState(isLoading = false, isEmptyList = true)
                _beerList.postValue(mutableListOf())
            }
        }
    }

    private fun validateQuery(query: String): Boolean = query.isNotEmpty()

    private fun String.replaceSpacesWithUnderScores(): String{
        return this.replace(" ", "_").lowercase()
    }

}