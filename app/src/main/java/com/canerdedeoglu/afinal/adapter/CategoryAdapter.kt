package com.canerdedeoglu.afinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerdedeoglu.afinal.CategoryFragmentDirections
import com.canerdedeoglu.afinal.R
import com.canerdedeoglu.afinal.models.Categories
import com.canerdedeoglu.afinal.ProfilFragmentDirections

class CategoryAdapter(private val context: Context, private val categoryList: List<Categories>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.name

        holder.itemView.setOnClickListener {
            // Önceki seçimi temizle
            val previousSelectedPosition = selectedItemPosition
            selectedItemPosition = holder.adapterPosition

            // Değişiklikleri bildir
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedItemPosition)

            // Tıklanan kategoriyi başka bir fragmente yönlendirme
            val action = CategoryFragmentDirections.actionToProduct(category.name)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = categoryList.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }
}
