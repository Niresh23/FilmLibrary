package com.geekbrains.team.filmlibrary.fragments.actorDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.adapters.ImagesAdapter
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
import com.geekbrains.team.filmlibrary.model.CreditsView
import com.geekbrains.team.filmlibrary.model.toPersonView
import com.geekbrains.team.filmlibrary.util.DiffUtilsCallback
import kotlinx.android.synthetic.main.full_actor_info_fragment.*
import kotlinx.android.synthetic.main.full_film_info_fragment.*
import kotlinx.android.synthetic.main.full_film_info_fragment.scrollView
import kotlinx.android.synthetic.main.full_series_info_fragment.*
import javax.inject.Inject

class FullActorInfoFragment: DaggerFragment() {
    private val args: FullActorInfoFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FullActorInfoViewModel> {viewModelFactory}
    lateinit var binding: FullActorInfoFragmentBinding

    private val infoAdapter by lazy {
        ImagesAdapter<PersonView, OnItemSelectedListener>(null, layout = R.layout.full_actor_info_item)
    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.full_actor_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
        viewModel.loadDetails(args.id)
        showActorDetails()
    }

    private fun startObservers() {
        viewModel.failure.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            binding.actorInfo = it

        })

        viewModel.movieCreditsLiveData.observe(viewLifecycleOwner, Observer {
            it.cast?.map { it.toPersonView() }.apply {
                val diffUtilCallback = DiffUtilsCallback(movieCastAdapter.data, this!!)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                movieCastAdapter.update(this)
                diffResult.dispatchUpdatesTo(movieCastAdapter)
            }

            it.crew?.map { it.toPersonView() }.apply {
                val diffUtilCallback = DiffUtilsCallback(movieCrewAdapter.data, this!!)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                movieCrewAdapter.update(this)
                diffResult.dispatchUpdatesTo(movieCrewAdapter)
            }
        })

        viewModel.tvCreditsLiveData.observe(viewLifecycleOwner, Observer {
            it.cast?.map { it.toPersonView() }.apply {
                val diffUtilCallback = DiffUtilsCallback(tvCastAdapter.data, this!!)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                tvCastAdapter.update(this)
                diffResult.dispatchUpdatesTo(tvCastAdapter)
            }

            it.crew?.map { it.toPersonView() }.apply {
                val diffUtilCallback = DiffUtilsCallback(tvCrewAdapter.data, this!!)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                tvCrewAdapter.update(this)
                diffResult.dispatchUpdatesTo(tvCrewAdapter)
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