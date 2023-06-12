package com.example.tanaapp.ui.favoritelist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tanaapp.R
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.databinding.FragmentFavoriteBinding
import com.example.tanaapp.ui.favoritelist.viewmodel.FavoriteListViewModel
import com.example.tanaapp.ui.home.MainActivity
import com.example.tanaapp.ui.viewstate.Status

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteListViewModel
    private lateinit var factory: FavoriteListViewModel.FavoriteListViewModelFactory
    private val adapter: FavoritesAdapter by lazy {FavoritesAdapter(arrayListOf(), this::goToDetail)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        factory = FavoriteListViewModel.FavoriteListViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(FavoriteListViewModel::class.java)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoritedList()

        viewModel.favState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.EMPTY -> {
                    binding.ivEmptyListFav.setImageResource(R.drawable.empty_list)
                    binding.ivEmptyListFav.isVisible = true
                    binding.tvEmptyListFav.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.rvFavorite.adapter = adapter
                    binding.rvFavorite.layoutManager = LinearLayoutManager(context)
                    adapter.updateList(it.data as MutableList<MenuItem>)
                    binding.rvFavorite.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.LOADING -> {
                    binding.pbLoading.isVisible = true
                    binding.rvFavorite.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
                    binding.pbLoading.isVisible = false
                }
                else -> {}
            }
        })
    }
    fun goToDetail(item: MenuItem){
        val bundle = bundleOf("ITEM_KEY" to item)
        NavHostFragment.findNavController(this).navigate(R.id.action_favoriteFragment_to_detailFragment,bundle)
    }
}