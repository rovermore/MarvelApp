package com.example.marvelapp.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.marvelapp.MarvelApp
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.model.Character
import com.example.marvelapp.utils.ScreenState
import com.example.marvelapp.utils.gone
import com.example.marvelapp.utils.visible
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailFragment : Fragment() {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val bundle = arguments
        val movieId = bundle?.getString("characterId")
        detailViewModel.initialize(movieId)
        setupObservers()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    init {
        MarvelApp.daggerAppComponent().inject(this)
    }

    private fun setupReloadButton() {
        binding.detailErrorView.reloadButton.setOnClickListener { detailViewModel.loadData() }
    }

    private fun setupObservers() {
        observeData()
        observeState()
    }

    private fun observeState() {
        detailViewModel.uiState.observe(viewLifecycleOwner, Observer {
            setupReloadButton()
            updateUI(it)
        })
    }

    private fun observeData() {
        detailViewModel.uiModel.observe(viewLifecycleOwner, Observer {
            setupUI(it)
        })
    }

    private fun setupUI(character: Character) {
        binding.nameTextView.text = character.name
        binding.descriptionTextView.text = character.description

        character.image?.run {
            Picasso.with(context)
                .load(character.image.getBigImageUrl())
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_marvel)
                .into(binding.posterImageView)
        }
    }

    private fun updateUI(it: ScreenState?) {
        when (it) {
            ScreenState.Error -> {
                binding.apply {
                    detailProgressBar.gone()
                    detailView.gone()
                    detailErrorView.apply {
                        errorImage.visible()
                        errorText.visible()
                        reloadButton.visible()
                    }
                }
            }
            ScreenState.Loading -> binding.detailProgressBar.visible()
            ScreenState.Success -> {
                binding.apply {
                    detailProgressBar.gone()
                    detailView.visible()
                }
            }
        }
    }
}