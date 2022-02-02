package com.macrosystems.beerfinder.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.macrosystems.beerfinder.R
import com.macrosystems.beerfinder.databinding.BeerDetailsFragmentBinding
import com.macrosystems.beerfinder.domain.model.Beer
import javax.inject.Inject

class BeerDetailsFragment @Inject constructor(private val glide: RequestManager) : Fragment() {

    private var _binding: BeerDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: BeerDetailsFragmentArgs? by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BeerDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        args?.let { args ->
            setUpUI(
                Beer(
                    name = args.beerDetails.name ?: getString(R.string.empty_placeholder),
                    image_url = args.beerDetails.image_url.orEmpty(),
                    description = args.beerDetails.description ?: getString(R.string.empty_placeholder),
                    alcoholByVolume = args.beerDetails.alcoholByVolume ?: getString(R.string.empty_placeholder)
                )
            )
        }
    }

    private fun setUpUI(beerDetails: Beer) {
        with(binding) {
            glide.load(
                beerDetails.image_url ?: AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.fall_back_image
                )
            ).into(ivDetailsBeerImage)
            tvBeerName.text = beerDetails.name
            tvDetailsAlcoholGrades.text =
                getString(R.string.degrees_details_placeholder, beerDetails.alcoholByVolume)
            tvBeerDescription.text = beerDetails.description

            btnGoBack.setOnClickListener { findNavController().popBackStack() }
        }
    }
}