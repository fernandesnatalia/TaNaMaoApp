package com.example.tanaapp.ui.end.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.tanaapp.R
import com.example.tanaapp.databinding.FragmentEndBinding
import com.example.tanaapp.ui.home.MainActivity

class EndFragment : Fragment() {
    private lateinit var binding: FragmentEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(inflater, container,false)
        binding.bvNewOrder.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_endFragment_to_splashActivity)
        }
        (activity as MainActivity).supportActionBar?.hide()
        return binding.root
    }
}