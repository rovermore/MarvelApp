package com.example.marvelapp.screen.main

import android.os.Bundle
import android.util.Log
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
import com.example.marvelapp.databinding.FragmentMainBinding
import com.example.marvelapp.model.Character
import com.example.marvelapp.utils.ScreenState
import com.example.marvelapp.utils.gone
import com.example.marvelapp.utils.visible
import javax.inject.Inject


class MainFragment : Fragment(), MainAdapter.OnItemClicked {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        layoutManager = GridLayoutManager(context,2)
        setupObservers()
        return binding.root
    }

    init { MarvelApp.daggerAppComponent().inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupReloadButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupReloadButton() {
        binding.errorView.reloadButton.setOnClickListener {
            adapter.clearMainAdapter()
            mainViewModel.initialize()
        }
    }

    private fun setupObservers() {
        observeData()
        observeState()
    }

    private fun observeState() {
        mainViewModel.uiState.observe(viewLifecycleOwner, Observer {
            screenState = it
            updateUI()
        })
    }

    private fun observeData() {
        mainViewModel.uiModel.observe(viewLifecycleOwner, Observer {
            adapter.updateTransactionList(it.toMutableList())
        })
    }

    private fun setupUI() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerView = binding.mainRecycler
        recyclerView.visibility = View.GONE
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
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
                Log.v("OFFSET: ", totalOffset.toString())
                mainViewModel.loadData(totalOffset)

            }
        }
    }

    private fun updateUI() {
        when (screenState) {
            ScreenState.Error -> {
                binding.apply {
                    progressBar.gone()
                    errorView.apply {
                        errorImage.visible()
                        errorText.visible()
                        reloadButton.visible()
                    }
                }
                recyclerView.gone()

            }
            ScreenState.Loading -> {
                binding.apply {
                    progressBar.apply {
                        visible()
                        bringToFront()
                    }
                }
            }
            ScreenState.Success -> {
                binding.progressBar.gone()
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