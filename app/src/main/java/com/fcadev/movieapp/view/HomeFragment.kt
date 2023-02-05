package com.fcadev.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.movieapp.R
import com.fcadev.movieapp.adapter.NowPlayingMovieAdapter
import com.fcadev.movieapp.adapter.TopRatedTvShowsAdapter
import com.fcadev.movieapp.databinding.FragmentHomeBinding
import com.fcadev.movieapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private val nowPlayingMovieAdapter = NowPlayingMovieAdapter(arrayListOf())
    private val topRatedTvShowsAdapter = TopRatedTvShowsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.downloadData()
        viewModel.downloadTopRatedData()

        binding.nowPlayingRecyclerView.adapter = nowPlayingMovieAdapter
        binding.nowPlayingRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.topRatedTvShowsRecyclerView.adapter = topRatedTvShowsAdapter
        binding.topRatedTvShowsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { nowPlayingMovies ->
            nowPlayingMovies?.let {
                binding.nowPlayingRecyclerView.visibility = View.VISIBLE
                nowPlayingMovieAdapter.updateNowPlayingMovieList(nowPlayingMovies)
            }
        })

        viewModel.topRatedTvShows.observe(viewLifecycleOwner, Observer { topRatedTvShows ->
            topRatedTvShows?.let {
                binding.topRatedTvShowsRecyclerView.visibility = View.VISIBLE
                topRatedTvShowsAdapter.updateTopRatedTvShowsList(topRatedTvShows)
            }
        })

        viewModel.nowPlayingMovieError.observe(viewLifecycleOwner, Observer { nowPlayingMoviesErrors ->
            nowPlayingMoviesErrors?.let {
                if (it){
                    binding.nowPlayingPageErrorText.visibility = View.VISIBLE
                }else {
                    binding.nowPlayingPageErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.nowPlayingMovieLoading.observe(viewLifecycleOwner, Observer { nowPlayingMoviesLoading ->
            nowPlayingMoviesLoading?.let {
                if (it){
                    binding.nowPlayingPageProgressBar.visibility = View.VISIBLE
                    binding.nowPlayingPageErrorText.visibility = View.GONE
                    binding.nowPlayingRecyclerView.visibility = View.GONE
                }else {
                    binding.nowPlayingPageProgressBar.visibility = View.GONE
                }
            }
        })

    }

}