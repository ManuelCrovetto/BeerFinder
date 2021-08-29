package com.macrosystems.beerfinder.ui.view.viewstates

data class BeerFinderViewState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: Boolean = false,
    val isEmptyList: Boolean = false
)