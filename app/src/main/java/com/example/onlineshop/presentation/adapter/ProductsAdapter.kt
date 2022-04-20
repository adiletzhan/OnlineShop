package com.example.onlineshop.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.data.models.Products
import com.example.onlineshop.databinding.ItemProductBinding

class ProductAdapter(
    private val products: Products,
    private val context: Context
    ): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(
        val binding: ItemProductBinding
        ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            val product = products[position]
            tvTitle.text = product.title
            tvPrice.text = product.price.toString()
            Glide.with(context)
                .load(product.image)
                .into(imageView)
        }
    }

    override fun getItemCount(): Int = products.size

}