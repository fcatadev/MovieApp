package com.fcadev.movieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.databinding.TopRatedMoviesRowBinding
import com.fcadev.movieapp.model.topratedmovies.TopRatedMoviesResult
import com.fcadev.movieapp.model.topratedtvshows.TopRatedTvShowsResult

class TopRatedMoviesAdapter(private val topRatedMoviesList: ArrayList<TopRatedMoviesResult>) : RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesViewHolder>() {

    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    var onItemClick : ((TopRatedMoviesResult) -> Unit)? = null

    class TopRatedMoviesViewHolder(var binding: TopRatedMoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMoviesViewHolder {
        val binding = TopRatedMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRatedMoviesViewHolder, position: Int) {

        val imageUrl = BASE_IMG_URL + topRatedMoviesList[position].backdrop_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.topRatedMoviesImage)
        holder.binding.topRatedMoviesName.text = topRatedMoviesList[position].title
        holder.binding.topRatedMoviesDate.text = topRatedMoviesList[position].release_date
        holder.binding.topRatedMoviesRateText.text = topRatedMoviesList[position].vote_average.toString()

        holder.binding.topRatedMoviesCardLayout.setOnClickListener {
            onItemClick?.invoke(topRatedMoviesList[position])

            val bundle = Bundle()
            bundle.putString("posterPath", topRatedMoviesList[position].poster_path)
            bundle.putString("originalTitle", topRatedMoviesList[position].original_title)
            bundle.putInt("id", topRatedMoviesList[position].id!!)
        }

    }

    override fun getItemCount(): Int {
        return topRatedMoviesList.size
    }

    fun updateTopRatedMoviesList(newTopRatedMoviesList: MutableList<TopRatedMoviesResult>){
        topRatedMoviesList.clear()
        topRatedMoviesList.addAll(newTopRatedMoviesList)
        notifyDataSetChanged()
    }

}