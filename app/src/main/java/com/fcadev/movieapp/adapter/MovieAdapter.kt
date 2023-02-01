package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.databinding.PopularListRowBinding
import com.fcadev.movieapp.model.trending.Result
import com.fcadev.movieapp.view.PopularMoviesFragmentDirections

class MovieAdapter(val movieList: ArrayList<Result>, var onItemClick: ((Result) -> Unit)? = null) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

    class MovieViewHolder(var binding: PopularListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            PopularListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imageUrl = BASE_IMG_URL + movieList[position].poster_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .into(holder.binding.popularMoviesListRowImg)

        if (movieList[position].original_name != null) { // dizi
            holder.binding.popularMoviesListRowText.text = movieList[position].original_name
            holder.binding.rateText.text = movieList[position].vote_average.toString()
        } else if (movieList[position].original_title != null) {
            holder.binding.popularMoviesListRowText.text = movieList[position].original_title
            holder.binding.rateText.text = movieList[position].vote_average.toString()
        }

        holder.binding.popularMovieCard.setOnClickListener {
            val action =
                PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updatePopularMovieList(newMovieList: MutableList<Result>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }
}