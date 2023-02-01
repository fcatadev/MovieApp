package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.movieapp.databinding.PopularListRowBinding
import com.fcadev.movieapp.model.Movies
import com.fcadev.movieapp.view.PopularMoviesFragmentDirections

class MovieAdapter(val movieList: ArrayList<Movies>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(var binding: PopularListRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = PopularListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.popularMoviesListRowText.text = movieList[position].movieName
        holder.binding.rateText.text = movieList[position].movieRate.toString()

        holder.binding.popularMovieCard.setOnClickListener {
            val action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updatePopularMovieList(newMovieList: List<Movies>){
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

}