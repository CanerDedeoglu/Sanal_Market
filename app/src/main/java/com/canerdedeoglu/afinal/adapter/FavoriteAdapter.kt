package com.canerdedeoglu.afinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canerdedeoglu.afinal.BegenilenUrunlerFragmentDirections
import com.canerdedeoglu.afinal.R
import com.canerdedeoglu.afinal.models.Favorite

class FavoriteAdapter(
    private val context: Context,
    private var favoriteList: List<Favorite>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favoriteList[position]
        holder.bind(favorite)


        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedItemPosition
            selectedItemPosition = holder.adapterPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedItemPosition)

            val action = BegenilenUrunlerFragmentDirections.actionFavoriteToDetails(product = null, favorite = favorite)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = favoriteList.size

    fun updateFavorites(newFavoriteList: List<Favorite>) {
        favoriteList = newFavoriteList
        notifyDataSetChanged()
    }

    // ViewHolder class
    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favoritePrice: TextView = itemView.findViewById(R.id.favoritePrice)
        private val favoriteImage: ImageView = itemView.findViewById(R.id.favoriteImage)
        private val favoriteName: TextView = itemView.findViewById(R.id.favoriteTitle)
        private val favoriteDescription: TextView = itemView.findViewById(R.id.favoriteDescription)

        fun bind(favorite: Favorite) {
            favoriteName.text = favorite.title
            favoriteDescription.text = favorite.description
            favoritePrice.text = "${favorite.price} TL"
            Glide.with(context).load(favorite.image).into(favoriteImage)

            // Update selected state
            itemView.isActivated = adapterPosition == selectedItemPosition
        }
    }
}
