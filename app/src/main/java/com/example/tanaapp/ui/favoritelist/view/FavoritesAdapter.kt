package com.example.tanaapp.ui.favoritelist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.databinding.ItemMenuBinding
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private var favoritedList: List<MenuItem>,
    private val clickDetail: (item: MenuItem) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showInfo(item: MenuItem){
            binding.tvItemTitle.text = item.name
            binding.tvItemDescription.text = item.description
            val value = "R$ ${item.value}"
            binding.tvItemValue.text = value
            Picasso.get().load(item.urlImageProduct).into(binding.ivItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favoritedList[position]
        holder.showInfo(item)
        holder.binding.cvMenuItem.setOnClickListener{
            clickDetail(item)
        }
    }

    override fun getItemCount() = favoritedList.size

    fun updateList(newList:MutableList<MenuItem>){
        favoritedList = newList
        notifyDataSetChanged()
    }
}