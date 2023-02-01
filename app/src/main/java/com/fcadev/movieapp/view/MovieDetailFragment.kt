package com.fcadev.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.FragmentMovieDetailBinding
import com.fcadev.movieapp.viewmodel.MovieDetailViewModel

class MovieDetailFragment : Fragment() {

    private var _binding : FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MovieDetailViewModel
    private var movieUuid = 0

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

        /*
        arguments?.let {
            movieUuid = MovieDetailFragmentArgs.fromBundle(it).movieUuid
        }
         */

        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        viewModel.getDataFromApi()

        observeLiveData()
    }

    private fun observeLiveData (){
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                binding.movieName.text = movie.movieName
                binding.ratingBar.rating = movie.movieRate!!
                binding.intergerRate.text = movie.movieRate.toString()
                binding.voteCount.text = movie.movieVote.toString()
                binding.movieDate.text = movie.movieDate
                binding.movieOverViewBody.text = movie.movieOverview
            }
        })
    }

}