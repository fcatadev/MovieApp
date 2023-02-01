package com.fcadev.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fcadev.movieapp.adapter.MovieAdapter
import com.fcadev.movieapp.databinding.FragmentPopularMoviesBinding
import com.fcadev.movieapp.viewmodel.PopularMoviesViewModel

class PopularMoviesFragment : Fragment() {

    private var _binding : FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PopularMoviesViewModel
    private val movieAdapter = MovieAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goPopToHomeBtn.setOnClickListener {
            val action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.goPopToFavBtn.setOnClickListener {
            val action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToFavoriteMoviesFragment()
            Navigation.findNavController(it).navigate(action)
        }

        viewModel = ViewModelProvider(this).get(PopularMoviesViewModel::class.java)
        viewModel.refreshData()

        binding.popularMovieList.adapter = movieAdapter
        binding.popularMovieList.layoutManager = LinearLayoutManager(context)

        movieAdapter.onItemClick = { movies ->
            Log.d("item onClick", movies.release_date.toString())
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                binding.popularMovieList.visibility = View.VISIBLE
                movieAdapter.updatePopularMovieList(movies)
            }
        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it){
                    binding.poplarPageErrorText.visibility = View.VISIBLE
                }else{
                    binding.poplarPageErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    binding.popularPageProgressBar.visibility = View.VISIBLE
                    binding.poplarPageErrorText.visibility = View.GONE
                    binding.popularMovieList.visibility = View.GONE
                }else {
                    binding.popularPageProgressBar.visibility = View.GONE
                }
            }
        })
    }

}