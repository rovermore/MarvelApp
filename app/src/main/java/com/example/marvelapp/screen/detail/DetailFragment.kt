package com.example.marvelapp.screen.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.marvelapp.MarvelApp
import com.example.marvelapp.R
import com.example.marvelapp.model.Character
import com.example.marvelapp.utils.ScreenState
import com.example.marvelapp.utils.gone
import com.example.marvelapp.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject



class DetailFragment : Fragment() {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val movieId = bundle?.getString("characterId")
        detailViewModel.initialize(movieId)
        setupObservers()
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

        init { MarvelApp.daggerAppComponent().inject(this) }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }

        private fun setupReloadButton() {
            reloadButton.setOnClickListener { detailViewModel.loadData() }
        }

        private fun setupObservers() {
            observeData()
            observeState()
        }

        private fun observeState() {
            detailViewModel.uiState.observe(this, Observer {
                setupReloadButton()
                updateUI(it)
            })
        }

        private fun observeData() {
            detailViewModel.uiModel.observe(this, Observer {
                setupUI(it)
            })
        }

        private fun setupUI(character: Character) {
            nameTextView.text = character.name
            descriptionTextView.text = character.description

            character.image?.run {
                Picasso.with(context)
                    .load(character.image.getBigImageUrl())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(posterImageView) }
        }

        private fun updateUI(it: ScreenState?) {
            when (it) {
                ScreenState.Error -> {
                    detailProgressBar.gone()
                    detailView.gone()
                    detailErrorView.visible()
                }
                ScreenState.Loading -> detailProgressBar.visible()
                ScreenState.Success -> {
                    detailProgressBar.gone()
                    detailView.visible()
                }
            }
        }
}