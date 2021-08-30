package com.macrosystems.beerfinder.data.model.parcelize

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//We do Parcelize this object to be able to use it through SafeArgs.
@Parcelize
data class BeerDetails (val name: String?, val image_url: String?, val description: String?, val alcoholByVolume: String?): Parcelable