package com.canerdedeoglu.afinal.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canerdedeoglu.afinal.R
import com.canerdedeoglu.afinal.models.Product

class ProductAdapter(private val context: Context, private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.title
        holder.productDescription.text = product.description
        holder.productPrice.text = product.price.toString() + " TL"
        Glide.with(context).load(product.images.firstOrNull()).into(holder.productImage)


        holder.itemView.setOnClickListener {
            // Önceki seçimi temizle
            val previousSelectedPosition = selectedItemPosition
            selectedItemPosition = holder.adapterPosition

            // Değişiklikleri bildir
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedItemPosition)

            // Tıklanan ürünü DetailsFragment'e göndermek için Bundle oluştur
            val bundle = Bundle().apply {
                putSerializable("product", product)
            }
            Navigation.findNavController(it).navigate(R.id.action_favorite_to_details, bundle)
        }
    }

    override fun getItemCount(): Int = productList.size

    fun updateProduct(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productTitle)
        val productDescription: TextView = itemView.findViewById(R.id.productDescription)
    }
}
