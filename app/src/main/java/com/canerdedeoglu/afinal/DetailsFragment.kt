package com.canerdedeoglu.afinal

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.canerdedeoglu.afinal.adapter.ReviewAdapter
import com.canerdedeoglu.afinal.databinding.FragmentDetailsBinding
import com.canerdedeoglu.afinal.models.Favorite
import com.canerdedeoglu.afinal.models.Product
import com.canerdedeoglu.afinal.models.ProductViewModel
import com.canerdedeoglu.afinal.models.Review
import com.google.gson.Gson

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    private var product: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        // ViewModel'ı init et
        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        val toolbar = (requireActivity() as MainActivity).binding.toolbar
        toolbar.title = "Ürün Detayı"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))
        toolbar.inflateMenu(R.menu.toolbar_menu)

        setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için

        val args = DetailsFragmentArgs.fromBundle(requireArguments())

        // Check if the argument is of type Product or Favorite
        if (args.product != null) {
            // It's a Product type
            product = args.product!!

            binding.txtProductTitle.text = product!!.title
            binding.txtPrice.text = product!!.price.toString() + " TL"
            binding.txtProductDescription.text = product!!.description
            binding.txtCategory.text = product!!.category
            binding.txtStock.text = product!!.stock.toString()

            Glide.with(requireContext())
                .load(product!!.images.firstOrNull())
                .into(binding.imgProduct)

            binding.recycleviewReviews.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleviewReviews.adapter = ReviewAdapter(product!!.reviews)
        } else if (args.favorite != null) {
            // It's a Favorite type
            val favorite = args.favorite!!

            binding.txtProductTitle.text = favorite.title
            binding.txtPrice.text = favorite.price.toString() + " TL"
            binding.txtProductDescription.text = favorite.description
            binding.txtCategory.text = favorite.category
            binding.txtStock.text = favorite.stock.toString()

            Glide.with(requireContext())
                .load(favorite.image)
                .into(binding.imgProduct)

            // Assuming favorite.review is a String or similar
            binding.recycleviewReviews.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleviewReviews.adapter = ReviewAdapter(Gson().fromJson(favorite.reviewJson, Array<Review>::class.java).toList())
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_like -> {
                product?.let { addToFavorites(it) }
                true
            }

            R.id.action_basket -> {
                product?.let { addToBasket(it) }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToBasket(product: Product) {
        try {
            val sharedPreferences =
                requireContext().getSharedPreferences("basket_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val currentBasket = sharedPreferences.getString("basket_items", "[]") ?: "[]"
            val basketItems =
                Gson().fromJson(currentBasket, Array<Product>::class.java).toMutableList()

            // Add new product to basket
            basketItems.add(product)

            // Save updated basket as JSON
            val updatedBasketJson = Gson().toJson(basketItems)
            editor.putString("basket_items", updatedBasketJson)
            editor.apply()

            Toast.makeText(requireContext(), "Ürün sepete eklendi!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("addToBasket", "Sepete ürün eklenirken hata oluştu: ${e.message}")
            Toast.makeText(
                requireContext(),
                "Ürün sepete eklenirken bir hata oluştu!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addToFavorites(product: Product) {
        val userId = getUserIdFromPreferences(requireContext()) ?: return
        val favorite = Favorite(
            userId = userId,
            productId = product.id,
            title = product.title,
            price = product.price,
            description = product.description,
            category = product.category,
            stock = product.stock,
            image = product.images.firstOrNull() ?: "",
            reviewJson = Gson().toJson(product.reviews)
        )

        viewModel.insertFavorite(favorite) // Favoriye ekleme
        Toast.makeText(requireContext(), "Ürün favorilere eklendi!", Toast.LENGTH_SHORT).show()
    }

    private fun getUserIdFromPreferences(context: Context): Int? {
        val sharedPreferences =
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_id", null)?.toIntOrNull()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
