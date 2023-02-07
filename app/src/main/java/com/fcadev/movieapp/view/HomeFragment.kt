package com.fcadev.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.R
import com.fcadev.movieapp.adapter.NowPlayingMovieAdapter
import com.fcadev.movieapp.adapter.TopRatedTvShowsAdapter
import com.fcadev.movieapp.databinding.FragmentHomeBinding
import com.fcadev.movieapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private val nowPlayingMovieAdapter = NowPlayingMovieAdapter(arrayListOf())
    private val topRatedTvShowsAdapter = TopRatedTvShowsAdapter(arrayListOf())
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

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

        topRatedTvShowsAdapter.onItemClick = {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)
            val btnClose: ImageView = view.findViewById(R.id.detailDialogExitButton)
            val detailDialogMovieName: TextView = view.findViewById(R.id.detailDialogMovieName)
            val detailDialogMovieDate: TextView = view.findViewById(R.id.detailDialogMovieDate)
            val detailDialogMovieOverview: TextView = view.findViewById(R.id.detailDialogMovieOverview)
            val detailDialogMovieImage: ImageView = view.findViewById(R.id.detailDialogMovieImage)
            val imageUrl = BASE_IMG_URL + it.poster_path

            detailDialogMovieName.text = it.name
            detailDialogMovieDate.text = it.first_air_date
            detailDialogMovieOverview.text = it.overview
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(detailDialogMovieImage)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(view)
            dialog.show()
        }

        nowPlayingMovieAdapter.onItemClick = {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)
            val btnClose: ImageView = view.findViewById(R.id.detailDialogExitButton)
            val detailDialogMovieName: TextView = view.findViewById(R.id.detailDialogMovieName)
            val detailDialogMovieDate: TextView = view.findViewById(R.id.detailDialogMovieDate)
            val detailDialogMovieOverview: TextView = view.findViewById(R.id.detailDialogMovieOverview)
            val detailDialogMovieImage: ImageView = view.findViewById(R.id.detailDialogMovieImage)
            val imageUrl = BASE_IMG_URL + it.poster_path

            detailDialogMovieName.text = it.title
            detailDialogMovieDate.text = it.release_date
            detailDialogMovieOverview.text = it.overview
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(detailDialogMovieImage)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(view)
            dialog.show()
        }

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