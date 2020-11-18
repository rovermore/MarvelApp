package com.example.marvelapp.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.MarvelApp
import com.example.marvelapp.R
import com.example.marvelapp.model.Character
import com.example.marvelapp.utils.ScreenState
import com.example.marvelapp.utils.gone
import com.example.marvelapp.utils.visible
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : Fragment(), MainAdapter.OnItemClicked {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView
    private var adapter = MainAdapter(null, this)
    private lateinit var layoutManager: GridLayoutManager
    private var screenState = ScreenState.Loading
    private var totalOffset = 0
    private val OFFSET = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutManager = GridLayoutManager(context,2)
        setupObservers()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    init { MarvelApp.daggerAppComponent().inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupReloadButton()
    }

    private fun setupReloadButton() {
        reloadButton.setOnClickListener {
            adapter.clearMainAdapter()
            mainViewModel.initialize()
        }
    }

    private fun setupObservers() {
        observeData()
        observeState()
    }

    private fun observeState() {
        mainViewModel.uiState.observe(this, Observer {
            screenState = it
            updateUI()
        })
    }

    private fun observeData() {
        mainViewModel.uiModel.observe(this, Observer {
            adapter.updateTransactionList(it.toMutableList())
        })
    }

    private fun setupUI() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerView = mainRecycler
        recyclerView.visibility = View.GONE
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        totalOffset = 0
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    setUpScrollLoader()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setUpScrollLoader() {
        val visibleItemCount = layoutManager.childCount
        val scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition()
        val totalCount = layoutManager.itemCount

        if (screenState != ScreenState.Loading) {
            if ((visibleItemCount + scrolledItems) > totalCount) {
                totalOffset += OFFSET
                mainViewModel.loadData(totalOffset)

            }
        }
    }

    private fun updateUI() {
        when (screenState) {
            ScreenState.Error -> {
                progressBar.gone()
                recyclerView.gone()
                errorView.visible()
            }
            ScreenState.Loading -> {
                progressBar.visible()
                progressBar.bringToFront()
            }
            ScreenState.Success -> {
                progressBar.gone()
                recyclerView.visible()
            }
        }
    }

    override fun itemClicked(character: Character) {
        navigateToDetailFragment(character.id.toString())
    }

    private fun navigateToDetailFragment(characterId: String) {
        val bundle = bundleOf("characterId" to characterId)
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }
}