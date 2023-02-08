package com.fcadev.movieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.PopularListRowBinding
import com.fcadev.movieapp.model.trending.Result
import com.fcadev.movieapp.view.PopularMoviesFragmentDirections
import java.math.RoundingMode


//import com.fcadev.movieapp.view.PopularMoviesFragmentDirections

class MovieAdapter(private val movieList: ArrayList<Result>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    var onItemClick: ((Result) -> Unit)? = null

    class MovieViewHolder(var binding: PopularListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val popularMoviesListRowImg: ImageView = binding.popularMoviesListRowImg
        val popularMoviesListRowText: TextView = binding.popularMoviesListRowText
        val rateText: TextView = binding.rateText

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            PopularListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imageUrl = BASE_IMG_URL + movieList[position].backdrop_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.popularMoviesListRowImg)

        //.apply(RequestOptions().override(100,100))

        val roundedVoteAverage =
            movieList[position].vote_average!!.toBigDecimal().setScale(1, RoundingMode.HALF_DOWN)
                .toFloat()

        if (movieList[position].original_name != null) { // dizi
            holder.binding.popularMoviesListRowText.text = movieList[position].original_name
            holder.binding.rateText.text = roundedVoteAverage.toString()
        } else if (movieList[position].original_title != null) {
            holder.binding.popularMoviesListRowText.text = movieList[position].original_title
            holder.binding.rateText.text = roundedVoteAverage.toString()
        }

        holder.binding.popularMovieCard.setOnClickListener {

            onItemClick?.invoke(movieList[position])

            val bundle = Bundle()
            bundle.putString("posterPath", movieList[position].poster_path)
            bundle.putString("originalTitle", movieList[position].original_title)
            bundle.putString("originalName", movieList[position].original_name)
            bundle.putFloat("voteAverage", roundedVoteAverage)
            bundle.putInt("voteCount", movieList[position].vote_count!!)
            bundle.putString("releaseDate", movieList[position].release_date)
            bundle.putString("firstAirDate", movieList[position].first_air_date)
            bundle.putString("overview", movieList[position].overview)
            bundle.putInt("id", movieList[position].id!!)

            findNavController(it).navigate(R.id.action_popularMoviesFragment_to_movieDetailFragment, bundle)
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