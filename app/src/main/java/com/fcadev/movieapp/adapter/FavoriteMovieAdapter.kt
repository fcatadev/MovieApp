package com.fcadev.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.FavoriteShowListRowBinding
import com.fcadev.movieapp.service.local.FavoriteMovie
import com.fcadev.movieapp.service.local.FavoriteMovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMovieAdapter(private var fragment: Fragment, private var favoriteMovie: List<FavoriteMovie>):
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder>(){

    val context = fragment.requireContext()
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

    class FavoriteViewHolder(var binding: FavoriteShowListRowBinding) : RecyclerView.ViewHolder(binding.root) {

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
        holder.binding.notFavButton.setOnClickListener {
            removeFromFavorites(favoriteMovie[position])

        }

    }

    override fun getItemCount(): Int {
        return favoriteMovie.size
    }

    private fun removeFromFavorites(favoriteMovie: FavoriteMovie){
        val database = Room.databaseBuilder(context, FavoriteMovieDatabase::class.java, "favorite_movies_db").build()
        val favoriteMovieDao = database.favoriteMovieDao()

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                favoriteMovieDao.delete(favoriteMovie)
                val updateList = favoriteMovieDao.getAllFavoriteMovies()
                this@FavoriteMovieAdapter.favoriteMovie = updateList
            }
            notifyDataSetChanged()
        }
        Toast.makeText(context, "İçerik favorilerden kaldırıldı", Toast.LENGTH_LONG).show()
    }
}