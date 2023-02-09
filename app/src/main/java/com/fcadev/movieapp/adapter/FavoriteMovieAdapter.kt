package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.fcadev.movieapp.databinding.FavoriteShowListRowBinding
import com.fcadev.movieapp.service.local.FavoriteMovie
import com.fcadev.movieapp.service.local.FavoriteMovieDao
import com.fcadev.movieapp.service.local.FavoriteMovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMovieAdapter(
    private var fragment: Fragment,
    private var favoriteMovieList: List<FavoriteMovie>,
    private var favoriteMovieDao: FavoriteMovieDao
) :
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
        val imageUrl = BASE_IMG_URL + favoriteMovieList[position].poster_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.favoriteMovieImage)

        val showName = if (favoriteMovieList[position].name != null){
            favoriteMovieList[position].name
        }else {
            favoriteMovieList[position].title
        }

        holder.binding.favoriteMovieName.text = showName
        holder.binding.notFavButton.setOnClickListener {
            removeFromFavorites(favoriteMovieList[position])
        }

    }

    override fun getItemCount(): Int {
        return favoriteMovieList.size
    }

    private fun removeFromFavorites(favoriteMovie: FavoriteMovie){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                favoriteMovieDao.delete(favoriteMovie)
                val updateList = favoriteMovieDao.getAllFavoriteMovies()
                favoriteMovieList = updateList
            }
            notifyDataSetChanged()
        }
        Toast.makeText(context, "İçerik favorilerden kaldırıldı", Toast.LENGTH_LONG).show()
    }
}