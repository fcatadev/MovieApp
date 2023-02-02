package com.fcadev.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
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


        /*
        binding.goHomeToPopBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPopularMoviesFragment()
            findNavController().navigate(action)
        }

        binding.goHomeToFavBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFavoriteMoviesFragment()
            Navigation.findNavController(it).navigate(action)
        }
         */

    }

}