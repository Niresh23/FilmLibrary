package com.geekbrains.team.filmlibrary.fragments.extension

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.adapters.ItemsAdapter
import com.geekbrains.team.filmlibrary.adapters.OnActorSelectedListener
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.adapters.UseCasesEnum
import com.geekbrains.team.filmlibrary.addOnScrollListenerPagination
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.model.PersonView
import com.geekbrains.team.filmlibrary.model.TVShowView
import com.geekbrains.team.filmlibrary.util.DiffUtilsCallback
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.extension_fragment.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ExtensionFragment: DaggerFragment() {

    private val args: ExtensionFragmentArgs by navArgs()

    private val mMovieAdapter: ItemsAdapter<MovieView, OnItemSelectedListener> by lazy {
        ItemsAdapter<MovieView, OnItemSelectedListener>(
            clickListener = onClickListener,
            layout = R.layout.landscape_card_item
        )
    }


    private val mActorAdapter: ItemsAdapter<PersonView, OnActorSelectedListener> by lazy {
        ItemsAdapter<PersonView, OnActorSelectedListener>(
            clickListener = onActorSelectedListener,
            layout = R.layout.landscape_actor_card_item
        )
    }

    private val mTVShowAdapter: ItemsAdapter<TVShowView, OnItemSelectedListener> by lazy {
        ItemsAdapter<TVShowView, OnItemSelectedListener>(
            clickListener = onClickListener,
            layout = R.layout.landscape_tv_show_card_item
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ExtensionViewModel> {viewModelFactory}

    lateinit var onClickListener: OnItemSelectedListener
    lateinit var onActorSelectedListener: OnActorSelectedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemSelectedListener && context is OnActorSelectedListener) {
            onClickListener = context
            onActorSelectedListener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        startObserve(args.useCasesArgs)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.extension_fragment,
            container, false)
    }

    private fun startObserve(argument: String) {
        when(argument) {
            UseCasesEnum.NOW_PLAYING_MOVIES.name -> viewModel.getData(argument)
            UseCasesEnum.UPCOMING_MOVIES.name -> viewModel.getData(argument)
            UseCasesEnum.MOVIE_CREW.name -> viewModel.getData(argument, args.id)
            UseCasesEnum.MOVIE_ACTORS.name -> viewModel.getData(argument, args.id)
            UseCasesEnum.SIMILAR_MOVIE.name -> viewModel.getData(argument, args.id)
            UseCasesEnum.ACTING_IN_MOVIES.name -> viewModel.getData(argument, args.id)
            UseCasesEnum.ACTING_IN_TV_SHOWS.name -> viewModel.getData(argument, args.id)
            UseCasesEnum.PRODUCT_MOVIES.name -> viewModel.getData(argument, args.id)
            UseCasesEnum.PRODUCT_TV_SHOWS.name -> viewModel.getData(argument, args.id)
            else -> throw IllegalArgumentException("Not such UseCase")
        }
    }

    private fun init() {
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            val diffUtilCallback = DiffUtilsCallback(mMovieAdapter.data, it)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            mMovieAdapter.update(it)
            diffResult.dispatchUpdatesTo(mMovieAdapter)
            rv_information.apply {
                val layoutManagerImpl = GridLayoutManager(context, 1)
                layoutManager = layoutManagerImpl
                adapter = mMovieAdapter
                addOnScrollListenerPagination(layoutManagerImpl) { viewModel.loadMore(args.useCasesArgs, id = args.id)}
            }
        })

        viewModel.personLiveData.observe(viewLifecycleOwner, Observer {
            val diffUtilCallback = DiffUtilsCallback(mActorAdapter.data, it)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            mActorAdapter.update(it)
            diffResult.dispatchUpdatesTo(mActorAdapter)
            rv_information.apply {
                val layoutManagerImpl = GridLayoutManager(context, 1)
                layoutManager = layoutManagerImpl
                adapter = mActorAdapter
            }
        })
    }
}