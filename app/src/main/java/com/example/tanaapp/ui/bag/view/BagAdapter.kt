package com.example.tanaapp.ui.bag.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.databinding.ItemBagBinding
import com.squareup.picasso.Picasso

class BagAdapter (
    private var cartList: List<MenuItem>,
    private val clickDetail: (item: MenuItem) -> Unit
) : RecyclerView.Adapter<BagAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemBagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showInfo(item: MenuItem){
            binding.tvItemTitle.text = item.name
            val value = (item.qtd * item.value).toString()
            binding.tvItemValue.text = value
            val qtd = "${item.qtd}x"
            binding.tvItemQt.text = qtd
            Picasso.get().load(item.urlImageProduct).into(binding.ivItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = cartList[position]
        holder.showInfo(menu)
        holder.binding.cvItemBag.setOnClickListener{
            clickDetail(menu)
        }
    }

    override fun getItemCount() = cartList.size

    fun updateList(newList:MutableList<MenuItem>){
        cartList = newList
        notifyDataSetChanged()
    }
}