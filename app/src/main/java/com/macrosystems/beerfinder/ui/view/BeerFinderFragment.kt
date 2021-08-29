package com.macrosystems.beerfinder.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.macrosystems.beerfinder.R
import com.macrosystems.beerfinder.core.dialog.ErrorDialog
import com.macrosystems.beerfinder.core.dialog.launcher.DialogFragmentLauncher
import com.macrosystems.beerfinder.core.ex.showDialog
import com.macrosystems.beerfinder.data.model.parcelize.BeerDetails
import com.macrosystems.beerfinder.databinding.BeerFinderFragmentBinding
import com.macrosystems.beerfinder.ui.adapters.BeersAdapter
import com.macrosystems.beerfinder.ui.view.viewstates.BeerFinderViewState
import com.macrosystems.beerfinder.ui.viewmodel.BeerFinderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class BeerFinderFragment @Inject constructor(
    private val adapter: BeersAdapter,
    private val dialogFragmentLauncher: DialogFragmentLauncher
) : Fragment() {

    private var _binding: BeerFinderFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BeerFinderViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BeerFinderFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
        initObservers()
        initBeersSearch()
        initRecyclerViewListeners()

    }

    private fun initRecyclerViewListeners() {
        adapter.onClickListener = {
            val action = BeerFinderFragmentDirections.actionBeerFinderFragmentToBeerDetailsFragment(
                BeerDetails(
                    name = it.name,
                    image_url = it.image_url,
                    description = it.description,
                    alcoholByVolume = it.alcoholByVolume
                )
            )
            findNavController().navigate(action)
        }
    }

    private fun initBeersSearch() {
        binding.editTextSearch.addTextChangedListener {
            it?.let {
                viewModel.searchImages(it.toString())
            }
        }
    }

    private fun initObservers() {
        with(viewModel) {
            beerList.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }

            lifecycleScope.launchWhenStarted {
                viewState.collect { viewState ->
                    updateUI(viewState)
                }
            }

        }
    }

    private fun updateUI(viewState: BeerFinderViewState) {
        with(binding) {
            progressBar.isVisible = viewState.isLoading
            clEmptyListLayout.isVisible = viewState.isEmptyList

            if (viewState.error) showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        ErrorDialog.create(
            description = getString(R.string.error_description_dialog_placeholder),
            isDialogCancelable = true,
            positiveAction = ErrorDialog.Action(getString(R.string.ok_placeholder)) {
                it.dismiss()
            }
        ).showDialog(dialogFragmentLauncher = dialogFragmentLauncher, requireActivity())
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            adapter = this@BeerFinderFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}