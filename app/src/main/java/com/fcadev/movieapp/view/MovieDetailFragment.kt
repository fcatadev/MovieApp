package com.fcadev.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.FragmentMovieDetailBinding
import com.fcadev.movieapp.viewmodel.MovieDetailViewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailViewModel
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

    private var posterPath: String = ""
    private var originalTitle: String = ""
    private var originalName: String = ""
    private var voteAverage: Float = 0f
    private var voteCount: Int = 0
    private var releaseDate: String = ""
    private var overview: String = ""


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
        getMovieDetails()
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