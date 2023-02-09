package com.fcadev.movieapp.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.FragmentMovieDetailBinding
import com.fcadev.movieapp.service.local.FavoriteMovie
import com.fcadev.movieapp.service.local.FavoriteMovieDao
import com.fcadev.movieapp.service.local.FavoriteMovieDatabase
import com.fcadev.movieapp.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailViewModel
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    private lateinit var favoriteMovieDao: FavoriteMovieDao
    //private lateinit var favoriteMovieModel : FavoriteMovie



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteMovieDatabase = Room.databaseBuilder(requireContext(), FavoriteMovieDatabase::class.java, "favorite_movie_db")
            .build()
        favoriteMovieDao = favoriteMovieDatabase.favoriteMovieDao()

        getMovieDetails()

        setupData()
    }

    private fun getMovieDetails() {
        val bundle = arguments
        if (bundle != null) {
            val args = MovieDetailFragmentArgs.fromBundle(bundle)

            val imageUrl = BASE_IMG_URL + args.posterPath
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(binding.movieDetailImage)

            if (args.originalTitle != null) {
                binding.movieName.text =  args.originalTitle.toString()
            }else if (args.originalName != null){
                binding.movieName.text =  args.originalName.toString()
            }else {
                binding.movieName.text = "Bir Hata İle Karşılaşıldı."
            }
            if (args.releaseDate != null){
                binding.releaseDate.text = args.releaseDate.toString()
            }else if (args.firstAirDate != null){
                binding.releaseDate.text = args.firstAirDate.toString()
            }else {
                binding.releaseDate.text = "Bir Hata İle Karşılaşıldı."
            }
            binding.voteAverage.text = args.voteAverage.toString()
            binding.voteCount.text = "(" + args.voteCount.toString() + ")"

            binding.movieOverViewBody.text = args.overview.toString()
            binding.ratingBar.rating = args.voteAverage
        }
    }

    private fun addMovieToFavorites(){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){

                val bundle = arguments
                val args = MovieDetailFragmentArgs.fromBundle(bundle!!)

                val favoriteMovie = FavoriteMovie(
                    args.id,
                    args.originalName,
                    args.originalTitle,
                    args.posterPath
                )

                val existingMovie = favoriteMovieDao.getMovieById(favoriteMovie.id)
                if (existingMovie == null){
                    favoriteMovieDao.insert(favoriteMovie)
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    withContext(Dispatchers.Main) {
                        showToast("İçerik başarıyla favorilerinize eklendi")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showToast("Bu içerik zaten favorilerinize ekli")
                    }
                }

            }
        }
    }

    private fun showToast(message: String){
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        toast.show()
    }

    private fun setupData(){
        binding.favoriteButton.setOnClickListener {
            addMovieToFavorites()
        }
    }

    /*private fun observeLiveData (){
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {

                *//*binding.movieName.text = movie.movieName
                binding.ratingBar.rating = movie.movieRate!!
                binding.intergerRate.text = movie.movieRate.toString()
                binding.voteCount.text = movie.movieVote.toString()
                binding.movieDate.text = movie.movieDate
                binding.movieOverViewBody.text = movie.movieOverview*//*
            }
        })
    }*/

}