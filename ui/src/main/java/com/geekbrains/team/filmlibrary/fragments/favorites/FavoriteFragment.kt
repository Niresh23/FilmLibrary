package com.geekbrains.team.filmlibrary.fragments.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.team.filmlibrary.MainActivity
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.adapters.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.favorite_fragment.*

class FavoriteFragment : Fragment() {

    private lateinit var listener: OnItemSelectedListener

    private val pageTitleList by lazy {
        arrayListOf(
            getString(R.string.movies),
            getString(R.string.tvShows),
            getString(R.string.waiting)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener.hideProgress()
        showInfo()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
    }

    private fun showInfo() {
        val mAdapter = ((context as MainActivity).supportFragmentManager).run {
            TabAdapter(
                this,
                this@FavoriteFragment.lifecycle,
                listOf(FavoriteMovieFragment(), FavoriteTVShowFragment(), WaitingFragment())
            )
        }

        mAdapter.let {
            viewPager.adapter = it
        }
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = pageTitleList.getOrNull(position)
        }.attach()
    }
}