package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.databinding.TopRatedTvShowsRowBinding
import com.fcadev.movieapp.model.topratedtvshows.TopRatedTvShowsResult

class TopRatedTvShowsAdapter(private val topRatedTvShowsList: ArrayList<TopRatedTvShowsResult>) : RecyclerView.Adapter<TopRatedTvShowsAdapter.TopRatedTvShowsViewHolder>() {

    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

    class TopRatedTvShowsViewHolder(var binding: TopRatedTvShowsRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedTvShowsViewHolder {
        val binding = TopRatedTvShowsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedTvShowsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRatedTvShowsViewHolder, position: Int) {

        val imageUrl = BASE_IMG_URL + topRatedTvShowsList[position].poster_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.topRatedTvShowsImage)

        holder.binding.topRatedTvShowsName.text = topRatedTvShowsList[position].name
        holder.binding.topRatedTvShowsDate.text = topRatedTvShowsList[position].first_air_date
        holder.binding.topRatedTvShowsRateText.text = topRatedTvShowsList[position].vote_average.toString()

    }

    override fun getItemCount(): Int {
        return topRatedTvShowsList.size
    }

    fun updateTopRatedTvShowsList(newTopRatedTvShowsList: MutableList<TopRatedTvShowsResult>){
        topRatedTvShowsList.clear()
        topRatedTvShowsList.addAll(newTopRatedTvShowsList)
        notifyDataSetChanged()
    }
}