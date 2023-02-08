package com.fcadev.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.fcadev.movieapp.adapter.FavoriteMovieAdapter
import com.fcadev.movieapp.databinding.FragmentFavoriteMoviesBinding
import com.fcadev.movieapp.service.local.FavoriteMovieDao
import com.fcadev.movieapp.service.local.FavoriteMovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMoviesFragment : Fragment() {

    private var _binding : FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteMovieDao: FavoriteMovieDao
    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteMovieDatabase = Room.databaseBuilder(
            requireContext(),
            FavoriteMovieDatabase::class.java,
            "favorite_movie_db"
        )
            .build()
        favoriteMovieDao = favoriteMovieDatabase.favoriteMovieDao()

        favoriteMovieAdapter = FavoriteMovieAdapter(emptyList())
        binding.favoriteShowListRecyclerView.adapter = favoriteMovieAdapter
        binding.favoriteShowListRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)

    }

    private fun getAllFavoriteMovies(){
        lifecycleScope.launch {
            val favoriteMovies = withContext(Dispatchers.IO) {
                favoriteMovieDao.getAllFavoriteMovies()
            }

            binding.favoriteShowListRecyclerView.adapter = FavoriteMovieAdapter(favoriteMovies)

        }
    }

    override fun onStart() {
        super.onStart()
        getAllFavoriteMovies()
    }

}