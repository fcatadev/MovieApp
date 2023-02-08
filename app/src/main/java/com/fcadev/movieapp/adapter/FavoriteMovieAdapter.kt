package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.databinding.FavoriteShowListRowBinding
import com.fcadev.movieapp.service.local.FavoriteMovie

class FavoriteMovieAdapter(private val favoriteMovie: List<FavoriteMovie>):
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder>(){

    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

    class FavoriteViewHolder(val binding: FavoriteShowListRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteShowListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val imageUrl = BASE_IMG_URL + favoriteMovie[position].poster_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.favoriteMovieImage)

        val showName = if (favoriteMovie[position].name != null){
            favoriteMovie[position].name
        }else {
            favoriteMovie[position].title
        }

        holder.binding.favoriteMovieName.text = showName
    }

    override fun getItemCount(): Int {
        return favoriteMovie.size
    }
}