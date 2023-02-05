package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcadev.movieapp.databinding.PopularListRowBinding
import com.fcadev.movieapp.model.trending.Result
import com.fcadev.movieapp.view.MainActivity
import com.fcadev.movieapp.view.PopularMoviesFragmentDirections
import java.math.RoundingMode

//import com.fcadev.movieapp.view.PopularMoviesFragmentDirections

class MovieAdapter(private val movieList: ArrayList<Result>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    var onItemClick : ((Result) -> Unit)? = null

    class MovieViewHolder(var binding: PopularListRowBinding) : RecyclerView.ViewHolder(binding.root) {

        val popularMoviesListRowImg : ImageView = binding.popularMoviesListRowImg
        val popularMoviesListRowText: TextView = binding.popularMoviesListRowText
        val rateText : TextView = binding.rateText

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = PopularListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imageUrl = BASE_IMG_URL + movieList[position].backdrop_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.popularMoviesListRowImg)

        //.apply(RequestOptions().override(100,100))

        val roundedVoteAverage = movieList[position].vote_average!!.toBigDecimal().setScale(1, RoundingMode.HALF_DOWN).toDouble()

        if (movieList[position].original_name != null) { // dizi
            holder.binding.popularMoviesListRowText.text = movieList[position].original_name
            holder.binding.rateText.text = roundedVoteAverage.toString()
        } else if (movieList[position].original_title != null) {
            holder.binding.popularMoviesListRowText.text = movieList[position].original_title
            holder.binding.rateText.text = roundedVoteAverage.toString()
        }

        holder.binding.popularMovieCard.setOnClickListener {

            onItemClick?.invoke(movieList[position])

            /*
            val action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment()
                //PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment()
            findNavController(it).navigate(action)
            //Navigation.findNavController(it).navigate(action)
             */
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