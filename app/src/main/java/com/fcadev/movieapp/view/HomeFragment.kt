package com.fcadev.movieapp.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.fcadev.movieapp.R
import com.fcadev.movieapp.adapter.NowPlayingMovieAdapter
import com.fcadev.movieapp.adapter.TopRatedMoviesAdapter
import com.fcadev.movieapp.adapter.TopRatedTvShowsAdapter
import com.fcadev.movieapp.databinding.FragmentHomeBinding
import com.fcadev.movieapp.service.local.FavoriteMovie
import com.fcadev.movieapp.service.local.FavoriteMovieDao
import com.fcadev.movieapp.service.local.FavoriteMovieDatabase
import com.fcadev.movieapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private val nowPlayingMovieAdapter = NowPlayingMovieAdapter(arrayListOf())
    private val topRatedTvShowsAdapter = TopRatedTvShowsAdapter(arrayListOf())
    private val topRatedMoviesAdapter = TopRatedMoviesAdapter(arrayListOf())
    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    private lateinit var favoriteMovieDao: FavoriteMovieDao
    private var existingMovie: FavoriteMovie? = null
    private lateinit var favoriteMovie: FavoriteMovie
    private lateinit var favoriteMovieDatabase: FavoriteMovieDatabase

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

        favoriteMovieDatabase = Room.databaseBuilder(requireContext(), FavoriteMovieDatabase::class.java, "favorite_movie_db")
            .allowMainThreadQueries().build()
        favoriteMovieDao = favoriteMovieDatabase.favoriteMovieDao()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.downloadData()
        viewModel.downloadTopRatedData()
        viewModel.downloadTopRatedMoviesData()

        binding.nowPlayingRecyclerView.adapter = nowPlayingMovieAdapter
        binding.nowPlayingRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.topRatedTvShowsRecyclerView.adapter = topRatedTvShowsAdapter
        binding.topRatedTvShowsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
        binding.topRatedMoviesRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        nowPlayingMovieShowDialog()
        observeLiveData()
    }

    private fun nowPlayingMovieShowDialog(){
        nowPlayingMovieAdapter.onItemClick = { nowPlayingRes ->
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)
            val favBtn: ImageView = view.findViewById(R.id.detailDialogFavoriteButton)
            val btnClose: ImageView = view.findViewById(R.id.detailDialogExitButton)
            val detailDialogMovieName: TextView = view.findViewById(R.id.detailDialogMovieName)
            val detailDialogMovieDate: TextView = view.findViewById(R.id.detailDialogMovieDate)
            val detailDialogMovieOverview: TextView = view.findViewById(R.id.detailDialogMovieOverview)
            val detailDialogMovieImage: ImageView = view.findViewById(R.id.detailDialogMovieImage)
            val imageUrl = BASE_IMG_URL + nowPlayingRes.poster_path
            val detailDialogId = nowPlayingRes.id

            if (favoriteMovieDao.getMovieById(detailDialogId) == null) {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            } else {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            }

            favoriteMovie = FavoriteMovie(
                nowPlayingRes.id,
                nowPlayingRes.original_title,
                nowPlayingRes.original_language,
                nowPlayingRes.poster_path
            )

            detailDialogMovieName.text = nowPlayingRes.title
            detailDialogMovieDate.text = nowPlayingRes.release_date
            detailDialogMovieOverview.text = nowPlayingRes.overview
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(detailDialogMovieImage)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            favBtn.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        existingMovie = favoriteMovieDao.getMovieById(detailDialogId)
                        if (existingMovie == null){
                            favoriteMovieDao.insert(favoriteMovie)
                            withContext(Dispatchers.Main){
                                showToast("İçerik başarıyla favorilerinize eklendi")
                                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }else {
                            withContext(Dispatchers.Main) {
                                showToast("Bu içerik zaten favorilerinize ekli")
                                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }
                    }
                }
            }
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(view)
            dialog.show()
        }

        topRatedTvShowsAdapter.onItemClick = { topRatedTvShowsRes ->
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)
            val favBtn: ImageView = view.findViewById(R.id.detailDialogFavoriteButton)
            val btnClose: ImageView = view.findViewById(R.id.detailDialogExitButton)
            val detailDialogMovieName: TextView = view.findViewById(R.id.detailDialogMovieName)
            val detailDialogMovieDate: TextView = view.findViewById(R.id.detailDialogMovieDate)
            val detailDialogMovieOverview: TextView = view.findViewById(R.id.detailDialogMovieOverview)
            val detailDialogMovieImage: ImageView = view.findViewById(R.id.detailDialogMovieImage)
            val imageUrl = BASE_IMG_URL + topRatedTvShowsRes.poster_path
            val detailDialogId = topRatedTvShowsRes.id

            if (favoriteMovieDao.getMovieById(detailDialogId) == null) {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            } else {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            }

            favoriteMovie = FavoriteMovie(
                topRatedTvShowsRes.id,
                topRatedTvShowsRes.name,
                topRatedTvShowsRes.original_language,
                topRatedTvShowsRes.poster_path
            )

            detailDialogMovieName.text = topRatedTvShowsRes.name
            detailDialogMovieDate.text = topRatedTvShowsRes.first_air_date
            detailDialogMovieOverview.text = topRatedTvShowsRes.overview
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(detailDialogMovieImage)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            favBtn.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        existingMovie = favoriteMovieDao.getMovieById(detailDialogId)
                        if (existingMovie == null){
                            favoriteMovieDao.insert(favoriteMovie)
                            withContext(Dispatchers.Main){
                                showToast("İçerik başarıyla favorilerinize eklendi")
                                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }else {
                            withContext(Dispatchers.Main) {
                                showToast("Bu içerik zaten favorilerinize ekli")
                                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }
                    }
                }
            }
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(view)
            dialog.show()
        }

        topRatedMoviesAdapter.onItemClick = { topRatedMoviesRes ->
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)
            val favBtn: ImageView = view.findViewById(R.id.detailDialogFavoriteButton)
            val btnClose: ImageView = view.findViewById(R.id.detailDialogExitButton)
            val detailDialogMovieName: TextView = view.findViewById(R.id.detailDialogMovieName)
            val detailDialogMovieDate: TextView = view.findViewById(R.id.detailDialogMovieDate)
            val detailDialogMovieOverview: TextView = view.findViewById(R.id.detailDialogMovieOverview)
            val detailDialogMovieImage: ImageView = view.findViewById(R.id.detailDialogMovieImage)
            val imageUrl = BASE_IMG_URL + topRatedMoviesRes.poster_path
            val detailDialogId = topRatedMoviesRes.id

            if (favoriteMovieDao.getMovieById(detailDialogId) == null) {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            } else {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            }

            favoriteMovie = FavoriteMovie(
                topRatedMoviesRes.id,
                topRatedMoviesRes.original_title,
                topRatedMoviesRes.original_language,
                topRatedMoviesRes.poster_path
            )

            detailDialogMovieName.text = topRatedMoviesRes.title
            detailDialogMovieDate.text = topRatedMoviesRes.release_date
            detailDialogMovieOverview.text = topRatedMoviesRes.overview
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(detailDialogMovieImage)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            favBtn.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        existingMovie = favoriteMovieDao.getMovieById(detailDialogId)
                        if (existingMovie == null){
                            favoriteMovieDao.insert(favoriteMovie)
                            withContext(Dispatchers.Main){
                                showToast("İçerik başarıyla favorilerinize eklendi")
                                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }else {
                            withContext(Dispatchers.Main) {
                                showToast("Bu içerik zaten favorilerinize ekli")
                                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }
                    }
                }
            }
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun showToast(message: String){
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        toast.show()
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

        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->
            topRatedMovies?.let {
                binding.topRatedMoviesRecyclerView.visibility = View.VISIBLE
                topRatedMoviesAdapter.updateTopRatedMoviesList(topRatedMovies)
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