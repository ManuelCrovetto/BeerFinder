package com.macrosystems.beerfinder.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.macrosystems.beerfinder.R
import com.macrosystems.beerfinder.data.model.BeerResponse
import javax.inject.Inject

class BeersAdapter @Inject constructor(private val glide: RequestManager): RecyclerView.Adapter<BeerViewHolder>() {

    private var beerList: MutableList<BeerResponse> = mutableListOf()

    var onClickListener: (BeerResponse) -> Unit = {}

    private val notifyItemHasChanged: (Int) -> Unit = { adapterPosition ->
        notifyItemChanged(adapterPosition)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<BeerResponse>){
        beerList.clear()
        beerList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return BeerViewHolder(itemView = itemView, glide = glide, notifyItemHasChanged = notifyItemHasChanged)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.onBind(beer = beerList[position], onClickListener)
    }

    override fun getItemCount(): Int = beerList.size

}