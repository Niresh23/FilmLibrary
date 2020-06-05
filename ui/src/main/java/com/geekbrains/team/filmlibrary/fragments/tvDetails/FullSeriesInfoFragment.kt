package com.geekbrains.team.filmlibrary.fragments.tvDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.geekbrains.team.filmlibrary.Const
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.adapters.*
import com.geekbrains.team.filmlibrary.databinding.FullSeriesInfoFragmentBinding
import com.geekbrains.team.filmlibrary.model.PersonView
import com.geekbrains.team.filmlibrary.model.TVShowView
import com.geekbrains.team.filmlibrary.util.DiffUtilsCallback
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.full_series_info_fragment.*
import kotlinx.android.synthetic.main.pager_indicator_item.*
import javax.inject.Inject

class FullSeriesInfoFragment: DaggerFragment(), OnLikeClickListener {
    private val args: FullSeriesInfoFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FullTvInfoViewModel> { viewModelFactory }
    lateinit var binding: FullSeriesInfoFragmentBinding
    private val onLikeClickListener: OnLikeClickListener = this
    private lateinit var onActorSelectedListener: OnActorSelectedListener
    private lateinit var onItemSelectedListener: OnItemSelectedListener


    private lateinit var mIndicator: Indicator<TVShowView, OnLikeClickListener>
    private val infoAdapter by lazy {
        ImagesAdapter<TVShowView, OnLikeClickListener>(clickListener = onLikeClickListener,
            layout = R.layout.full_series_info_item)
    }

    private val actorsAdapter by lazy {
        ItemsAdapter<PersonView, OnActorSelectedListener>(clickListener = onActorSelectedListener,
            layout = R.layout.small_actor_card_item)
    }

    private val similarTVShowAdapter by lazy {
        ItemsAdapter<TVShowView, OnItemSelectedListener>(clickListener = onItemSelectedListener,
            layout = R.layout.small_series_card_item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnActorSelectedListener) {
            onActorSelectedListener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }

        if (context is OnItemSelectedListener) {
            onItemSelectedListener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.full_series_info_fragment,
            null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onItemSelectedListener.showProgress()
        initUI()
        startObservers()
        savedInstanceState ?: loadMovieDetails()
        showMovieDetails()
    }

    override fun onResume() {
        super.onResume()
        infoAdapter.clear()
        loadMovieDetails()
    }

    private fun startObservers() {
        viewModel.failure.observe(viewLifecycleOwner, Observer { msg ->
            Toast.makeText(context, msg.localizedMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.tvDetailsLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                infoAdapter.data = it
                mIndicator.startIndicators()
                mIndicator.setCurrentIndicator(0)
                binding.tvShow = it
                onItemSelectedListener.hideProgress()
            }
        })

        viewModel.actorsLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                val diffUtilCallback = DiffUtilsCallback(actorsAdapter.data, it)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                actorsAdapter.update(it)
                diffResult.dispatchUpdatesTo(actorsAdapter)
            }
        })

        viewModel.similarTVShowsLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                val diffUtilCallback = DiffUtilsCallback(similarTVShowAdapter.data, it)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                similarTVShowAdapter.update(it)
                diffResult.dispatchUpdatesTo(similarTVShowAdapter)
            }
        })
    }

    private fun initUI() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        mIndicator = Indicator(context, indicator, indicator_item, infoAdapter)
        description_tv.setOnClickListener {
            if (description_tv.maxLines == Const.DESCRIPTION_MAX_LINES)
                description_tv.maxLines = Int.MAX_VALUE
            else description_tv.maxLines = Const.DESCRIPTION_MAX_LINES
        }
    }

    private fun loadMovieDetails() {
        viewModel.loadTVShowInfo(args.id)
        viewModel.loadSimilarTVShows(args.id, 1)
    }

    private fun showMovieDetails() {
        topPager.apply {
            adapter = infoAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mIndicator.setCurrentIndicator(position)

                }
            })
        }

        actors_rv.apply {
            adapter = actorsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,
                false)
        }

        similar_rv.apply {
            adapter = similarTVShowAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,
                false)
        }
    }

    override fun onLikeClick(id: Int) {
        viewModel.addInFavorite(id)
    }
}