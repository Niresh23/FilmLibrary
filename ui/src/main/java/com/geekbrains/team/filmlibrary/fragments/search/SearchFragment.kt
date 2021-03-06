package com.geekbrains.team.filmlibrary.fragments.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.team.filmlibrary.MainActivity
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.adapters.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    private lateinit var listener: OnItemSelectedListener

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SearchViewModel>({ activity as MainActivity }) { viewModelFactory }

    private val pageTitleList by lazy {
        arrayListOf(
            getString(R.string.movies),
            getString(R.string.tvShows)
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
    ): View? = inflater.inflate(R.layout.search_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener.hideProgress()

        initUI()
        showInfo()
    }

    private fun initUI() {
        search_et.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                search_et.text.toString().let {
                    viewModel.currentQuery = it
                    viewModel.loadSearchedMovies()
                    viewModel.loadSearchedTV()
                }
            }
            true
        }
    }

    private fun showInfo() {

        viewPager.adapter = childFragmentManager.run {
            TabAdapter(
                this,
                this@SearchFragment.lifecycle,
                listOf(SearchMovieFragment(), SearchTVShowFragment())
            )
        }

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = pageTitleList.getOrNull(position)
        }.attach()
    }
}