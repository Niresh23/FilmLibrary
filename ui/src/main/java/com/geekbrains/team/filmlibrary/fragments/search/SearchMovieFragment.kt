package com.geekbrains.team.filmlibrary.fragments.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.team.filmlibrary.MainActivity
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.adapters.ItemsAdapter
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.util.DiffUtilsCallback
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search_inner_fragment.*
import javax.inject.Inject

class SearchMovieFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SearchViewModel>({ activity as MainActivity }) { viewModelFactory }
    private lateinit var listener: OnItemSelectedListener

    private val searchedMovieAdapter: ItemsAdapter<MovieView> by lazy(mode = LazyThreadSafetyMode.NONE) {
        ItemsAdapter<MovieView>(
            clickListener = listener,
            layout = R.layout.landscape_search_item,
            onScrollToLastPageListener = {
                viewModel.loadSearchedMovies(viewModel.currentQuery, ++viewModel.currentMoviePage)
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.search_inner_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        startObservers()
    }

    private fun initViews() {
        with(inner_recycler) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = MergeAdapter(searchedMovieAdapter)
        }
    }

    private fun startObservers() {
        viewModel.failure.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.searchedMoviesData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
//                // TODO HIDE PB

                searchedMovieAdapter.isAllItemsLoaded = it.isEmpty()

                if (searchedMovieAdapter.isAllItemsLoaded) return@let

                val oldList = searchedMovieAdapter.data.toList()

                if (viewModel.newMoviePage) {
                    searchedMovieAdapter.addItems(it)
                } else {
                    searchedMovieAdapter.update(it)
                }

                val diffUtilCallback = DiffUtilsCallback(oldList, searchedMovieAdapter.data)
                DiffUtil.calculateDiff(diffUtilCallback).dispatchUpdatesTo(searchedMovieAdapter)
            }
        })
    }
}