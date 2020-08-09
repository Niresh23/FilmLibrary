package com.geekbrains.team.filmlibrary.fragments.actorDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.databinding.FullActorInfoFragmentBinding
import com.geekbrains.team.filmlibrary.model.PersonView
import dagger.android.support.DaggerFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.team.filmlibrary.adapters.ItemsAdapter
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.model.toPersonView
import com.geekbrains.team.filmlibrary.util.DiffUtilsCallback
import kotlinx.android.synthetic.main.full_actor_info_fragment.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
class FullActorInfoFragment: DaggerFragment() {
    private val args: FullActorInfoFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FullActorInfoViewModel> {viewModelFactory}
    lateinit var binding: FullActorInfoFragmentBinding
    lateinit var onItemSelectedListener: OnItemSelectedListener

    private val movieCastAdapter by lazy {
        ItemsAdapter<PersonView, OnItemSelectedListener>(null, layout = R.layout.small_actor_card_item)
    }

    private val movieCrewAdapter by lazy {
        ItemsAdapter<PersonView, OnItemSelectedListener>(null, layout = R.layout.small_actor_card_item)
    }

    private val tvCastAdapter by lazy {
        ItemsAdapter<PersonView, OnItemSelectedListener>(null, layout = R.layout.small_actor_card_item)
    }

    private val tvCrewAdapter by lazy {
        ItemsAdapter<PersonView, OnItemSelectedListener>(null, layout = R.layout.small_actor_card_item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.full_actor_info_fragment,
            null, false)
        binding.listener = onItemSelectedListener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
        viewModel.loadDetails(args.id)
        viewModel.loadMovieCredits(args.id)
        viewModel.loadTVCredits(args.id)
        showActorDetails()
    }

    private fun startObservers() {
        viewModel.failure.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            Log.d("my log", it.message ?: "NO MESSAGE")
        })

        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            binding.actorInfo = it
            Log.d("my log", it.biography)
            Log.d("my log", it.profilePath ?: "NO POSTER")
        })

        viewModel.movieCreditsLiveData.observe(viewLifecycleOwner, Observer {
            it.cast?.map { cast -> cast.toPersonView() }?.apply {
                val diffUtilCallback = DiffUtilsCallback(movieCastAdapter.data, this)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                movieCastAdapter.update(this)
                diffResult.dispatchUpdatesTo(movieCastAdapter)
                Log.d("my log", this[0].name)
            }
            it.crew?.map { crew -> crew.toPersonView() }?.apply {
                val diffUtilCallback = DiffUtilsCallback(movieCrewAdapter.data, this)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                movieCrewAdapter.update(this)
                diffResult.dispatchUpdatesTo(movieCrewAdapter)
                Log.d("my log", this[0].name)
            }
        })

        viewModel.tvCreditsLiveData.observe(viewLifecycleOwner, Observer { it ->
            it.cast?.map { cast -> cast.toPersonView() }?.apply {
                val diffUtilCallback = DiffUtilsCallback(tvCastAdapter.data, this)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                tvCastAdapter.update(this)
                diffResult.dispatchUpdatesTo(tvCastAdapter)
                Log.e("my log", this[0].name)
            }
            it.crew?.map {crew -> crew.toPersonView() }?.apply {
                val diffUtilCallback = DiffUtilsCallback(tvCrewAdapter.data, this)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                tvCrewAdapter.update(this)
                diffResult.dispatchUpdatesTo(tvCrewAdapter)
                Log.e("my log", this[0].name)
            }
        })
    }

    private fun showActorDetails() {

        credits_cast_movies_rv.apply {
            adapter = movieCastAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,
                false)
        }

        credits_cast_tvshows_rv.apply {
            adapter = tvCastAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,
                false)
        }

        credits_crew_movies_rv.apply {
            adapter = movieCrewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,
                false)
        }

        credits_crew_tvshows_rv.apply {
            adapter = tvCrewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,
                false)
        }
    }


}