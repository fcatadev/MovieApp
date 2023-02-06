package com.fcadev.movieapp.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.BottomSheetDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : BottomSheetDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //inflater.inflate(R.layout.bottom_sheet_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailDialogExitButton.setOnClickListener{
            Toast.makeText(context, "Clicked Exit Button", Toast.LENGTH_LONG).show()
        }

    }

}