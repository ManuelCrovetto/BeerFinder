package com.macrosystems.beerfinder.ui.adapters


import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.RequestManager
import com.macrosystems.beerfinder.R
import com.macrosystems.beerfinder.core.ex.span
import com.macrosystems.beerfinder.data.model.BeerResponse
import com.macrosystems.beerfinder.databinding.ItemListBinding

class BeerViewHolder (itemView: View, private val glide: RequestManager, private val notifyItemHasChanged: (Int) -> Unit): RecyclerView.ViewHolder(itemView){

    private val binding = ItemListBinding.bind(itemView)
    private val context = binding.root.context

    fun onBind(beer: BeerResponse, onClickListener: (BeerResponse) -> Unit){
        setUpBeerImage(beer.image_url)
        setUpCopies(beer)

        itemView.setOnClickListener { onItemViewSelected(beer) }
        binding.btnSeeDetails.setOnClickListener { onClickListener(beer) }
    }

    private fun onItemViewSelected(beer: BeerResponse) {
        beer.isExpanded = !beer.isExpanded

        if (adapterPosition != NO_POSITION) notifyItemHasChanged(adapterPosition)
    }

    private fun setUpCopies(beer: BeerResponse) {
        binding.tvNameAndAlcoholGrades.text = context.span(beer.name, beer.alcoholByVolume + "º")
        binding.btnSeeDetails.isVisible = beer.isExpanded
    }


    private fun setUpBeerImage(imageUrl: String?) {
        glide.load(imageUrl ?: AppCompatResources.getDrawable(context, R.drawable.fall_back_image)).into(binding.ivBeerImage)
    }
}