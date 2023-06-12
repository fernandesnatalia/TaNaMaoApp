package com.example.tanaapp.ui.bag.view

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
import com.example.tanaapp.databinding.FragmentBagBinding
import com.example.tanaapp.ui.bag.viewmodel.BagViewModel
import com.example.tanaapp.ui.home.MainActivity
import com.example.tanaapp.ui.viewstate.Status

class BagFragment : Fragment() {
    private lateinit var binding: FragmentBagBinding
    private lateinit var viewModel: BagViewModel
    private lateinit var factory: BagViewModel.BagModelFactory
    private val adapter: BagAdapter by lazy { BagAdapter(arrayListOf(), this::goToDetail) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View {
        binding = FragmentBagBinding.inflate(layoutInflater, container, false)
        factory = BagViewModel.BagModelFactory()
        viewModel = ViewModelProvider(this,factory).get(BagViewModel::class.java)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBagList()

        binding.bvCloseOrder.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_bagFragment_to_endFragment)
        }

        viewModel.bagState.observe(viewLifecycleOwner, Observer{
            when(it.status){
                Status.EMPTY -> {
                    binding.ivEmptyListFav.setImageResource(R.drawable.empty_list)
                    binding.ivEmptyListFav.isVisible = true
                    binding.tvEmptyListFav.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.rvCart.adapter = adapter
                    binding.rvCart.layoutManager = LinearLayoutManager(context)
                    adapter.updateList(it.data as MutableList<MenuItem>)
                    binding.rvCart.isVisible = true
                    binding.pbLoading.isVisible = false
                    totalOrder(it.data)
                }
                Status.LOADING -> {
                    binding.pbLoading.isVisible = true
                    binding.rvCart.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText( context,"${it.message}", Toast.LENGTH_LONG).show()
                    binding.pbLoading.isVisible = false
                }
                else -> {}
            }
        })
    }

    fun goToDetail(item: MenuItem){
        val bundle = bundleOf("ITEM_KEY" to item)
        NavHostFragment.findNavController(this).navigate(R.id.action_bagFragment_to_detailFragment,bundle)
    }
    fun totalOrder(list: List<MenuItem>){
        var value = 0.0
        for(dish in list){
            value = (value + dish.value * dish.qtd)
        }
        val string = getString(R.string.total)
        binding.bvTotalOrder.text = string + (value.toString())
    }
}