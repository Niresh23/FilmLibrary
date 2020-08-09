package com.geekbrains.team.filmlibrary.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.databinding.*
import com.geekbrains.team.filmlibrary.fragments.search.DateSettings
import com.geekbrains.team.filmlibrary.model.PersonView
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.model.TVShowView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.big_card_item.view.*

object ViewHolderFactory {
    fun create(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.small_card_item -> SmallCardHolder(
                SmallCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.upcoming_small_card_item -> UpcomingSmallCardHolder(
                UpcomingSmallCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.landscape_card_item -> LandscapeCardHolder(
                LandscapeCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.landscape_tv_show_card_item -> LandscapeTVCardHolder(
                LandscapeTvShowCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.big_card_item -> TopRatedCardHolder(
                BigCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.full_film_info_item -> FullInfoCardHolder(
                FullFilmInfoItemBinding.inflate(inflater, parent, false)
            )
            R.layout.small_actor_card_item -> SmallActorCardItemHolder(
                SmallActorCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.full_series_info_item -> FullSeriesInfoCardHolder(
                FullSeriesInfoItemBinding.inflate(inflater, parent, false)
            )
            R.layout.small_series_card_item -> SmallSeriesCardHolder(
                SmallSeriesCardItemBinding.inflate(inflater, parent, false)
            )
            R.layout.landscape_actor_card_item -> LandscapeActorCardHolder(
                LandscapeActorCardItemBinding.inflate(inflater, parent, false)
            )

            else -> throw Exception("Wrong view type")
        }
    }

    class SmallCardHolder(
        private val binding: SmallCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnItemSelectedListener
            binding.movie = data as? MovieView
            binding.executePendingBindings()
        }
    }

    class UpcomingSmallCardHolder(
        private val binding: UpcomingSmallCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnItemSelectedListener
            binding.movie = data as? MovieView
            binding.executePendingBindings()
        }
    }

    class LandscapeCardHolder(
        private val binding: LandscapeCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnItemSelectedListener
            binding.movie = data as? MovieView
            binding.executePendingBindings()
        }
    }

    class LandscapeTVCardHolder(
        private val binding: LandscapeTvShowCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnItemSelectedListener
            binding.tvShow = data as? TVShowView
            binding.executePendingBindings()
        }
    }

    class TopRatedCardHolder(
        private val binding: BigCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        private val play: ImageButton = binding.root.play_btn
        private val backDrop: ImageView = binding.root.title_iv

        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnItemSelectedListener
            binding.movie = data as? MovieView

            binding.movie?.let {
                binding.executePendingBindings()
                play.setOnClickListener { v: View ->
                    v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.trailer)))
                }
                Picasso.get().load(it.images[position]).into(backDrop)
            }
        }
    }

    class FullInfoCardHolder(
        private val binding: FullFilmInfoItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        private val play: ImageButton = binding.root.play_btn
        private val backDrop: ImageView = binding.root.title_iv

        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.movie = data as? MovieView
            binding.movie?.let {
                binding.listener = listener as OnLikeClickListener
                binding.executePendingBindings()
                play.setOnClickListener { v: View ->
                    v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.trailer)))
                }
                Picasso.get().load(it.images[position]).into(backDrop)
            }
        }
    }

    class SmallActorCardItemHolder(
        private val binding: SmallActorCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnActorSelectedListener
            binding.actor = data as? PersonView
            binding.executePendingBindings()
        }
    }

    class FullSeriesInfoCardHolder(
        private val binding: FullSeriesInfoItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        private val play: ImageButton = binding.root.play_btn
        private val backDrop: ImageView = binding.root.title_iv

        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.tvShow = data as? TVShowView
            binding.tvShow?.let {
                binding.listener = listener as OnLikeClickListener
                binding.executePendingBindings()
                play.setOnClickListener { v: View ->
                    v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.trailer)))
                }
                Picasso.get().load(it.images[position]).into(backDrop)
            }
        }
    }

    class SmallSeriesCardHolder(
        private val binding: SmallSeriesCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnItemSelectedListener
            binding.tvShow = data as? TVShowView
            binding.executePendingBindings()
        }

    }

    class LandscapeActorCardHolder(
        private val binding: LandscapeActorCardItemBinding
    ): RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnActorSelectedListener
            binding.actorInfo = data as? PersonView
            binding.executePendingBindings()
        }

    }
}