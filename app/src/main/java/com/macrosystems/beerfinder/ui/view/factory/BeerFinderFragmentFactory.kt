package com.macrosystems.beerfinder.ui.view.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.macrosystems.beerfinder.core.dialog.launcher.DialogFragmentLauncher
import com.macrosystems.beerfinder.ui.adapters.BeersAdapter
import com.macrosystems.beerfinder.ui.view.BeerDetailsFragment
import com.macrosystems.beerfinder.ui.view.BeerFinderFragment
import javax.inject.Inject

//Creating a FragmentFactory we are able to inject constructors in the fragments.
//Visit https://developer.android.com/reference/androidx/fragment/app/FragmentFactory
class BeerFinderFragmentFactory @Inject constructor(
    private val adapter: BeersAdapter,
    private val glide: RequestManager,
    private val dialogFragmentLauncher: DialogFragmentLauncher
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            BeerFinderFragment::class.java.name -> {
                BeerFinderFragment(adapter, dialogFragmentLauncher)
            }
            BeerDetailsFragment::class.java.name -> {
                BeerDetailsFragment(glide)
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }
    }
}