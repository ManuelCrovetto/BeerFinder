package com.macrosystems.beerfinder.domain.model

import android.os.Parcelable
import com.macrosystems.beerfinder.data.model.BeerModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beer(
    val name: String?,
    val image_url: String?,
    val description: String?,
    val alcoholByVolume: String?,
    var isExpanded: Boolean = false
): Parcelable

fun BeerModel.toDomain() = Beer(
    name = name,
    image_url = image_url,
    description = description,
    alcoholByVolume = alcoholByVolume,
    isExpanded = isExpanded
)