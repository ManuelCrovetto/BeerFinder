package com.macrosystems.beerfinder.data.model

import com.google.gson.annotations.SerializedName

data class BeerModel(
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("description") val description: String,
    @SerializedName("abv") val alcoholByVolume: String,
    var isExpanded: Boolean = false
)