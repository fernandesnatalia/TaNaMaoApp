package com.example.tanaapp.ui.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tanaapp.R
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.databinding.FragmentDetailBinding
import com.example.tanaapp.ui.detail.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var factory: DetailViewModel.DetailModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        factory = DetailViewModel.DetailModelFactory()
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataRecovery()
    }

    private fun dataRecovery() {
        val item = arguments?.getParcelable<MenuItem>("ITEM_KEY")
        item?.let {
            Picasso.get().load(it.urlImageProduct).into(binding.ivItemDetail)
            binding.tvItemTitle.text = it.name
            binding.tvItemDescription.text = it.description
            val value = "${getString(R.string.item_price)} ${it.value}"
            binding.tvItemPrice.text = value
            binding.tvQuantity.text = it.qtd.toString()
            updateColor(item)
        }
        var num = 1
        binding.tvLess.setOnClickListener{
            num -= 1
            if(num > 0 ){
                binding.tvQuantity.text = num.toString()
                if (item != null) {
                    item.qtd = num
                }
            }
        }

        binding.tvMore.setOnClickListener{
            num += 1
            if(num > 0 ){
                binding.tvQuantity.text = num.toString()
                if (item != null) {
                    item.qtd = num
                }
            }
        }
        binding.tvBagAdd.setOnClickListener {
            if (item != null) {
                addItemBag(item)
            }
        }
        binding.ivFavorite.setOnClickListener {
            if (item != null) {
                item.isFavorite = !item.isFavorite
                viewModel.updateFavoritedList(item)
                favoriteItem(item)
                verifyIconFavorite(item.isFavorite)

            }
        }
    }

    private fun addItemBag(item: MenuItem) {
        viewModel.sendItemToBag(item)
        this.view?.let { Snackbar.make(it, R.string.item_add, Snackbar.LENGTH_SHORT).show() }

    }

    private fun updateColor(item: MenuItem) {
        var isFavorite = false
        var listFavorite = viewModel.favoriteList
        listFavorite?.forEach {
            if (it.name == item.name) {
                isFavorite = true
            }
        }
        verifyIconFavorite(isFavorite)
    }

    private fun verifyIconFavorite(isFavorite: Boolean) {
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable
                (
                binding.root.context,
                if (isFavorite) {
                    R.drawable.ic_fav
                } else {
                    R.drawable.ic_fav_empty
                }
            )
        )
    }

    private fun favoriteItem(item: MenuItem) {
        if (item.isFavorite) {
            this.view?.let { Snackbar.make(it, "${item.name} ${getString(R.string.item_fav)}", Snackbar.LENGTH_SHORT).show() }
        } else {
            this.view?.let { Snackbar.make(it,"${item.name} ${getString(R.string.item_disfav)}", Snackbar.LENGTH_SHORT).show() }
        }
    }
}